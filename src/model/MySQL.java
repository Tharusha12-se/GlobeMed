package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class MySQL {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/globemed";
    private static final String USER = "root";
    private static final String PASSWORD = "Sltharusha1234";

    static {
        initializeConnection();
    }

    private static void initializeConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to initialize database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Connection is closed, creating new connection...");
                initializeConnection();
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("Error checking connection status: " + e.getMessage());
            initializeConnection();
            return connection;
        }
    }

    public static Connection getFreshConnection() throws SQLException {
        // Close existing connection and create a brand new one
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Closed existing connection.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
        
        // Create new connection
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Created fresh database connection.");
            return connection;
        } catch (SQLException e) {
            System.err.println("Failed to create fresh connection: " + e.getMessage());
            throw e;
        }
    }

    public static ResultSet execute(String query) throws Exception {
        Connection conn = getFreshConnection(); // Use fresh connection
        Statement statement = conn.createStatement();
        
        if (query.startsWith("SELECT")) {
            return statement.executeQuery(query);
        } else {
            statement.executeUpdate(query);
            return null;
        }
    }

    public static int executeUpdate(String query) {
        try {
            Connection conn = getFreshConnection(); // Use fresh connection
            Statement statement = conn.createStatement();
            return statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static ResultSet execute(String string, Date sqlDate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static boolean isConnectionValid() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method for prepared statements with fresh connections
    public static PreparedStatement prepareStatement(String sql) throws SQLException {
        return getFreshConnection().prepareStatement(sql);
    }

    // Helper method for transactions
    public static void beginTransaction() throws SQLException {
        Connection conn = getFreshConnection();
        conn.setAutoCommit(false);
    }

    public static void commitTransaction() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    public static void rollbackTransaction() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.rollback();
            connection.setAutoCommit(true);
        }
    }
}