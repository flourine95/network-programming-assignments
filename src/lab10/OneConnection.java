package lab10;


import lab10.dao.StudentDAO;
import lab10.dao.UserDAO;
import lab10.models.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class OneConnection implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private boolean loggedIn;


    public OneConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.studentDAO = new StudentDAO();
        this.userDAO = new UserDAO();
        this.loggedIn = false;
    }

    @Override
    public void run() {
        try {
            String username;
            String password;
            while (true) {
                username = in.readLine();
                password = in.readLine();
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                if (userDAO.checkLogin(username, password)) {
                    out.println("Login successful! You can now use student commands.");
                    break;
                } else {
                    out.println("Invalid username or password. Please try again.");
                }
            }

            // Sau khi đăng nhập thành công, cho phép người dùng nhập các lệnh CRUD
            String clientRequest;
            while (true) {
                out.println("Enter a command (CREATE, READ, UPDATE, DELETE, QUIT): ");
                clientRequest = in.readLine();

                switch (clientRequest.toUpperCase()) {
                    case "CREATE":
                        // Code để tạo sinh viên
                        out.println("Enter student name: ");
                        String name = in.readLine();
                        out.println("Enter student score: ");
                        double score = Double.parseDouble(in.readLine());
                        studentDAO.create(new Student(name, score));
                        out.println("Student created successfully!");
                        break;
                    case "READ":
                        // Code để đọc danh sách sinh viên
                        out.println("Fetching all students...");
                        out.println(studentDAO.all().toString());
                        break;
                    case "UPDATE":
                        // Code để cập nhật sinh viên
                        out.println("Enter student ID to update: ");
                        int studentIdToUpdate = Integer.parseInt(in.readLine());
                        out.println("Enter new student name: ");
                        String newName = in.readLine();
                        out.println("Enter new student score: ");
                        double newScore = Double.parseDouble(in.readLine());
                        studentDAO.update(new Student(studentIdToUpdate, newName, newScore));
                        out.println("Student updated successfully!");
                        break;
                    case "DELETE":
                        // Code để xóa sinh viên
                        out.println("Enter student ID to delete: ");
                        int studentIdToDelete = Integer.parseInt(in.readLine());
                        studentDAO.destroy(studentIdToDelete);
                        out.println("Student deleted successfully!");
                        break;
                    case "QUIT":
                        out.println("Goodbye!");
                        return;
                    default:
                        out.println("Invalid command. Please try again.");
                        break;
                }
            }
        } catch (IOException e) {
            out.println("An error occurred: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
