package lab11.v4.repositories;

import lab11.v4.models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private final Connection dbConnection;

    public StudentRepository(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Student> all() throws SQLException {
        String sql = "SELECT * FROM students";
        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                students.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getDouble("score")));
            }
            return students;
        }
    }

    public List<Student> filter(String name) throws SQLException {
        String sql = "SELECT * FROM students WHERE name LIKE ?";
        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name);
            ResultSet rs = pstmt.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                students.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getDouble("score")));
            }
            return students;
        }
    }

    public boolean update(Student student) throws SQLException {
        String sql = "UPDATE students SET name = ?, score = ? WHERE id = ?";

        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setDouble(2, student.getScore());
            pstmt.setInt(3, student.getId());
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public boolean create(Student student) throws SQLException {
        String sql = "INSERT INTO students ( name, score) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setDouble(2, student.getScore());
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        }
    }

    public boolean destroy(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";

        try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }

}
