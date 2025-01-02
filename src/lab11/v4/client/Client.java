package lab11.v4.client;

import lab11.v4.models.Student;
import lab11.v4.services.ISessionManager;
import lab11.v4.services.IStudentManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Client {
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
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Age: ");
                    int age = scanner.nextInt();
                    boolean result = studentManager.create(sessionId, new Student(name, age));
                    if (result) {
                        System.out.println("Thêm sinh viên thành công!");
                    } else {
                        System.out.println("Thêm sinh viên thất bại!");
                    }
                }
                case 3 -> {
                    System.out.print("ID cần xóa: ");
                    int removeId = scanner.nextInt();

                    boolean result = studentManager.destroy(sessionId, removeId);
                    if (result) {
                        System.out.println("Xóa sinh viên thành công!");
                    } else {
                        System.out.println("Xóa sinh viên thất bại!");
                    }
                }
                case 4 -> {
                    System.out.print("ID cần cập nhật: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Name: ");
                    String updateName = scanner.nextLine();
                    System.out.print("Age: ");
                    int updateAge = scanner.nextInt();

                    boolean result = studentManager.update(sessionId, new Student(updateId, updateName, updateAge));
                    if (result) {
                        System.out.println("Cập nhật sinh viên thành công!");
                    } else {
                        System.out.println("Cập nhật sinh viên thất bại!");
                    }
                }
                case 5 -> {
                    System.out.print("Nhập tên sinh viên cần tìm: ");
                    String name = scanner.nextLine();
                    List<Student> students = studentManager.filter(sessionId, name);
                    students.forEach(System.out::println);
                }
                case 6 -> {
                    sessionManager.logout(sessionId);
                    System.out.println("Đã đăng xuất!");
                    System.exit(0);
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
