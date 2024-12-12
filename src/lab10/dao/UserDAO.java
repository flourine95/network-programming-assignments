package lab10.dao;


import lab10.models.User;

import java.sql.*;

public class UserDAO {

    private DatabaseConnection dbConnection;

    public UserDAO() {
        dbConnection = new DatabaseConnection();
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

    }

    public boolean checkLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public User getUserByEmail(String email) {
        User user = null;
        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String fullname = rs.getString("fullname");
                    user = new User(id, email, fullname);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

 

}

