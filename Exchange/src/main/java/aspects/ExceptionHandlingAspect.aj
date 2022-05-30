package aspects;

import org.aspectj.lang.Signature;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public aspect ExceptionHandlingAspect {
    private static Logger logger = Logger.getLogger(ExceptionHandlingAspect.class.getName());

    Connection around() throws SQLException: execution(Connection *.getConnection() throws SQLException) {
        final int max_retries = 3;

        int retry = 0;
        while (true) {
            try {
                return proceed();
            } catch (SQLException ex) {
                logger.info("Encountered " + ex);
                if (++retry > max_retries) {
                    throw ex;
                }
                logger.info("\t Retrying...");
            }
        }
    }

    after() throwing(Exception e): call(* *.*()) {
        Signature s = thisJoinPoint.getSignature();
        logger.throwing(s.getDeclaringType().getName(), s.getName(), e);
    }
}
