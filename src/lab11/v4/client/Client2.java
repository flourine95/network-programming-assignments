package lab11.v4.client;

import lab11.v4.services.ISessionManager;
import lab11.v4.services.IStudentManager;
import lab11.v4.models.Student;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Client2 {
    private static String sessionId = null;

    public static void main(String[] args) throws RemoteException, NotBoundException, SQLException {
        Registry registry = LocateRegistry.getRegistry("localhost", 2000);

        ISessionManager sessionManager = (ISessionManager) registry.lookup("SessionManager");
        IStudentManager studentManager = (IStudentManager) registry.lookup("StudentManager");

        Scanner scanner = new Scanner(System.in);

        while (sessionId == null) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            sessionId = sessionManager.login(username, password);
            System.out.println("sessionId: " + sessionId);
            if (sessionId != null) {
                System.out.println("Login successful! Session ID: " + sessionId);
            } else {
                System.out.println("Login failed! Please try again.");
            }
        }

        while (true) {
            System.out.println("1. Xem danh sách sinh viên");
            System.out.println("2. Thêm sinh viên");
            System.out.println("3. Xóa sinh viên");
            System.out.println("4. Câp nhật sinh viên");
            System.out.println("5. Tìm kiếm sinh viên theo tên");
            System.out.println("6. Thoát");
            System.out.print("Chọn: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    List<Student> students = studentManager.all(sessionId);
                    students.forEach(System.out::println);
                }
                case 2 -> {
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Age: ");
                    int age = scanner.nextInt();
                    studentManager.create(sessionId, new Student(id, name, age));
                    System.out.println("Thêm sinh viên thành công!");
                }
                case 3 -> {
                    System.out.print("ID cần xóa: ");
                    int removeId = scanner.nextInt();
                    studentManager.destroy(sessionId, removeId);
                    System.out.println("Xóa sinh viên thành công!");
                }
                case 4 -> {
                    System.out.print("ID cần cập nhật: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Name: ");
                    String updateName = scanner.nextLine();
                    System.out.print("Age: ");
                    int updateAge = scanner.nextInt();
                    studentManager.update(sessionId, new Student(updateId, updateName, updateAge));
                    System.out.println("Cập nhật sinh viên thành công!");
                }
                case 5 -> {
                    System.out.print("Nhập tên sinh viên cần tìm: ");
                    String name = scanner.nextLine();
                    List<Student> students = studentManager.filter(sessionId, name);
                    students.forEach(System.out::println);
                }
                case 6 -> {
                    // Đăng xuất và thoát
                    sessionManager.logout(sessionId);
                    System.out.println("Đã đăng xuất!");
                    System.exit(0);
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
