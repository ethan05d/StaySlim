package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;

public class DBConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/StaySlim"
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=UTC";
    private static final String USER = "stayslim";
    private static final String PASSWORD = "EthanAndRohanPassword";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to load JDBC driver");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        // Check for non-fatal warnings
        SQLWarning warn = conn.getWarnings();
        while (warn != null) {
            System.out.println("SQL Warning: " + warn.getMessage());
            System.out.println("State  : " + warn.getSQLState());
            System.out.println("Error  : " + warn.getErrorCode());
            warn = warn.getNextWarning();
        }
        return conn;
    }
}