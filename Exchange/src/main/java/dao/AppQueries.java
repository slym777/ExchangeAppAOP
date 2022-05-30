package dao;

public class AppQueries {

    public static final String CREATE_USER_DB_QUERY =
            "CREATE TABLE IF NOT EXISTS users (" +
            "u_id INT(64) PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT," +
            "u_firstname VARCHAR(20) NOT NULL," +
            "u_lastname VARCHAR(20) NOT NULL," +
            "u_username VARCHAR(30) NOT NULL," +
            "u_password VARCHAR(30) NOT NULL," +
            "u_country VARCHAR(30)," +
            "u_address VARCHAR(30)" +
            ")";

    public static final String CREATE_CURRENCY_DB_QUERY =
            "CREATE TABLE IF NOT EXISTS currency (" +
            "c_id INT(64) PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT," +
            "c_name VARCHAR(20) NOT NULL," +
            "c_symbol VARCHAR(5) NOT NULL," +
            "c_code VARCHAR(3) NOT NULL," +
            "c_quantity DOUBLE(30, 4),"+
            "c_rate DOUBLE(30, 4)" +
            ")";

    public static final String UPDATE_CURRENCY_QUANTITY_BY_CODE = "UPDATE currency SET c_quantity = ? WHERE c_code = ?";

}
