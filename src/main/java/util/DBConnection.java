package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;

// DB Connection JDBC class
public class DBConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/StaySlim"
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=UTC";

    // Database credentials (match what you configured in MySQL server)
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try {
            // load the MySQL JDBC driver class
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded successfully");

        } catch (ClassNotFoundException e) {
            System.err.println("Unable to load the Driver class");
        }
    }

    // Opens and returns a new Connection to the database
    public static Connection getConnection() throws SQLException {
        // get the connection using our credentials
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        // return the live connection for use by DAOs
        return conn;
    }
}
