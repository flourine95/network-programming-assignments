package lab11.v1.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_FILE = "\\src\\lab11\\v1\\data\\database.accdb";
    private static final String DB_URL = "jdbc:ucanaccess://" + System.getProperty("user.dir") + DB_FILE;
    private static Connection connection;
    private static final String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);  // Tải driver
            return DriverManager.getConnection(DB_URL);  // Trả về kết nối
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;  // Nếu kết nối thất bại, trả về null
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
