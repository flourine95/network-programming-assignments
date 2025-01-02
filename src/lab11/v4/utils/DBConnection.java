package lab11.v4.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_FILE = "\\src\\lab11\\v4\\data\\database.accdb";
    private static final String DB_URL = "jdbc:ucanaccess://" + System.getProperty("user.dir") + DB_FILE;
    private static final String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

    private final Connection connection;

    public DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        connection = DriverManager.getConnection(DB_URL);
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
