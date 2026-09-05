package virinchi.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3307/elitewear_db";
    private static final String USER = "root";
    private static final String PASS = ""; // XAMPP default is empty password

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); // Old driver for older MySQL connector
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Database connection successful!");
        } catch (Exception e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
        return conn;
    }
    
    // Test method
    public static void main(String[] args) {
        Connection con = getConnection();
        if (con != null) {
            System.out.println("✅✅ CONNECTION TEST PASSED!");
        } else {
            System.out.println("❌❌ CONNECTION TEST FAILED!");
        }
    }
}