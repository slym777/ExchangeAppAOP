package aspects;

import model.Currency;
import utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Logger;

import static dao.AppQueries.*;

public aspect PersistentAspect {
    private static Logger logger = Logger.getLogger(PersistentAspect.class.getName());

    double around(Currency currency, double quan): (execution(public double addCurrencyQuantity(Currency, double ))
        || execution(public double subtractCurrencyQuantity(Currency , double ))) && args(currency, quan) {

        double val = proceed(currency, quan);
        Connection conn = null;
        PreparedStatement pStmt = null;
        try {
            conn = ConnectionUtils.getConnection();
            String sqlCommand = UPDATE_CURRENCY_QUANTITY_BY_CODE;

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setDouble(1, val);
            pStmt.setString(2, currency.getCurrencyCode());

            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pStmt != null) {
                try {
                    pStmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("Updated quantity for " + currency.getCurrencyCode() + " with " +
                (thisJoinPoint.getSignature().getName().contains("add") ? "+" : "-") + quan);
        return val;
    }

    void around(): initialization(dao.daoimpl.*.new()) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = ConnectionUtils.getConnection();
            stmt = conn.createStatement();

            String sqlCommand = thisJoinPoint.getSignature().getDeclaringTypeName().contains("Currency") ?
                    CREATE_CURRENCY_DB_QUERY : CREATE_USER_DB_QUERY;
            stmt.execute(sqlCommand);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("Executed create table query for " + thisJoinPoint.getSignature().getDeclaringTypeName());

    }

}
