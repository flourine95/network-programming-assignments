package lab10.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_FILE = "\\src\\lab10\\data\\database.accdb";
    private static final String DB_URL = "jdbc:ucanaccess://" + System.getProperty("user.dir") + DB_FILE;

    private static Connection connection;

    private static void initConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connection established successfully.");
        }
    }

    public static Connection getConnection() throws SQLException {
        initConnection(); 
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.err.println("Connection closed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Database URL: " + DB_URL);

            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
