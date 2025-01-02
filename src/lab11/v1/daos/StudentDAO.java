package lab11.v1.daos;

import lab11.v1.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Students");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double score = rs.getDouble("score");
                students.add(new Student(id, name, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return students;
    }

    public List<Student> findById(int id) {
        List<Student> result = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Students WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int studentId = rs.getInt("id");
                String name = rs.getString("name");
                double score = rs.getDouble("score");
                result.add(new Student(studentId, name, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return result;
    }

    public List<Student> findByName(String namePart) {
        List<Student> result = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Students WHERE Name LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + namePart + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double score = rs.getDouble("score");
                result.add(new Student(id, name, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return result;
    }

    public static void main(String[] args) {
        new StudentDAO().getAllStudents().forEach(System.out::println);

        System.out.println(new StudentDAO().findById(3));
    }
}
