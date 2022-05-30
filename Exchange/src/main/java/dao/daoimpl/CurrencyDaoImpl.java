package dao.daoimpl;

import dao.CurrencyDao;
import model.Currency;
import model.ExchangeRate;
import utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrencyDaoImpl implements CurrencyDao {
//    @Override
//    public void createCurrencyTable() {
//        Connection conn = null;
//        Statement stmt = null;
//
//        try {
//            conn = ConnectionUtils.getConnection();
//            stmt = conn.createStatement();
//
//            String sqlCommand = "CREATE TABLE IF NOT EXISTS currency (" +
//                                "c_id INT(64) PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT," +
//                                "c_name VARCHAR(20) NOT NULL," +
//                                "c_symbol VARCHAR(5) NOT NULL," +
//                                "c_code VARCHAR(3) NOT NULL," +
//                                "c_quantity DOUBLE(30, 4),"+
//                                "c_rate DOUBLE(30, 4)" +
//                                ")";
//            stmt.execute(sqlCommand);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    @Override
    public void insert(ExchangeRate exchangeRate) {
        Connection conn = null;
        PreparedStatement pStmt = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "INSERT INTO currency (c_name, c_symbol, c_code, c_rate)" +
                                "VALUES (?, ?, ?, ?)";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setString(1, exchangeRate.getCurrencyName());
            pStmt.setString(2, exchangeRate.getCurrencySymbol());
            pStmt.setString(3, exchangeRate.getCurrencyCode());
            pStmt.setDouble(4, exchangeRate.getRate());

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
    }

    @Override
    public void update(int id, ExchangeRate exchangeRate) {
        Connection conn = null;
        PreparedStatement pStmt = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "UPDATE currency SET c_name = ?, c_symbol = ?, c_code = ?, c_rate = ? WHERE c_id = ?";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setString(1, exchangeRate.getCurrencyName());
            pStmt.setString(2, exchangeRate.getCurrencySymbol());
            pStmt.setString(3, exchangeRate.getCurrencyCode());
            pStmt.setDouble(4, exchangeRate.getRate());
            pStmt.setInt(5, id);

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
    }

    @Override
    public void update(Currency currency, Double quantity) {
        Connection conn = null;
        PreparedStatement pStmt = null;

        try {
            conn = ConnectionUtils.getConnection();

            int id = selectByCode(currency.getCurrencyCode());
            String sqlCommand = "UPDATE currency SET c_quantity = ? WHERE c_id = ?";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setDouble(1, quantity);
            pStmt.setInt(2, id);

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
    }

    @Override
    public void delete(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "DELETE FROM currency WHERE c_id = ?";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setInt(1, id);

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
    }

    @Override
    public int selectByCode(String code) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "SELECT c_id FROM currency WHERE c_code = ?";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setString(1, code);

            resultSet = pStmt.executeQuery();

            int c_id = -1;
            while (resultSet.next()) {
                c_id = resultSet.getInt("c_id");
            }

            return c_id;
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

        return -1;
    }

    @Override
    public ExchangeRate selectCurrencyById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "SELECT c_name, c_symbol, c_code, c_rate FROM currency WHERE c_id = ?";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setInt(1, id);

            resultSet = pStmt.executeQuery();

            ExchangeRate result = new ExchangeRate();
            while (resultSet.next()) {
                result.setCurrencyName(resultSet.getString("c_name"));
                result.setCurrencySymbol(resultSet.getString("c_symbol"));
                result.setCurrencyCode(resultSet.getString("c_code"));
                result.setRate(resultSet.getDouble("c_rate"));
            }

            return result;
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

        return null;
    }

    @Override
    public Double selectQuantityById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "SELECT c_quantity FROM currency WHERE c_id = ?";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setInt(1, id);

            resultSet = pStmt.executeQuery();

            double result = -1.0;

            while (resultSet.next()) {
                result = resultSet.getDouble("c_quantity");
            }

            return result;
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
        return null;
    }


    @Override
    public void
    insertAll(List<ExchangeRate> exchangeRates, Map<Currency, Double> quantity) {
        for (ExchangeRate exchangeRate: exchangeRates) {
            insert(exchangeRate);
        }

        for (Map.Entry<Currency, Double> entry: quantity.entrySet()) {
            update(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public List<Currency> selectAllCurrencies() {
        List<Currency> currencies = new ArrayList<>();


        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "SELECT c_name, c_code, c_symbol " +
                                "FROM currency ";

            pStmt = conn.prepareStatement(sqlCommand);

            resultSet = pStmt.executeQuery();

            while (resultSet.next()) {
                Currency currency = new Currency();
                currency.setCurrencyName(resultSet.getString("c_name"));
                currency.setCurrencyCode(resultSet.getString("c_code"));
                currency.setCurrencySymbol(resultSet.getString("c_symbol"));

                currencies.add(currency);
            }
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

        return currencies;
    }
}
