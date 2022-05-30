package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
    private static ConnectionUtils ourInstance = new ConnectionUtils();

    public static ConnectionUtils getInstance() {
        return ourInstance;
    }

    private ConnectionUtils() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/ExchangeDB?serverTimezone=UTC", "root", "root");
    }
}