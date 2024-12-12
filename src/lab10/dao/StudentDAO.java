package lab10.dao;


import lab10.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private DatabaseConnection dbConnection;

    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.all().forEach(System.out::println);
    }

    public StudentDAO() {
        dbConnection = new DatabaseConnection();
    }

    public List<Student> all() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double score = rs.getDouble("score");
                students.add(new Student(id, name, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public Student findById(int id) {
        Student student = null;
        String query = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    double score = rs.getDouble("score");
                    student = new Student(id, name, score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    public void create(Student student) {
        String query = "INSERT INTO students (name, average_score) VALUES (?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, student.getName());
            pstmt.setDouble(2, student.getScore());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Student student) {
        String query = "UPDATE students SET name = ?, average_score = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, student.getName());
            pstmt.setDouble(2, student.getScore());
            pstmt.setInt(3, student.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void destroy(int id) {
        String query = "DELETE FROM students WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
}

