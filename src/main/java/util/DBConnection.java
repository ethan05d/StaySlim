package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;

public class DBConnection {
    // Make sure name of connection is StaySlim
    private static final String URL =
            "jdbc:mysql://localhost:3306/StaySlim"
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=UTC";

    // Use the correct USER and PASSWORD you provided within MYSQL Workbench
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.err.println("Unable to load the Driver class");
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        SQLWarning warn = conn.getWarnings();
        while (warn != null) {
            System.out.println("SQL Warning: " + warn.getMessage());
        }

        return conn;
    }
}