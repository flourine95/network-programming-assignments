package lab7.learn;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 2000;

        try (
                Socket socket = new Socket(serverAddress, port);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Kết nối đến server thành công!");

            // Nhập lệnh từ người dùng
            System.out.print("Nhập lệnh (UPLOAD): ");
            String command = scanner.nextLine();
            dos.writeUTF(command); // Gửi lệnh đến server

            if ("UPLOAD".equalsIgnoreCase(command)) {
                // Nhập đường dẫn nguồn và đích
                System.out.print("Nhập đường dẫn nguồn: ");
                String sourcePath = scanner.nextLine();
                System.out.print("Nhập đường dẫn đích: ");
                String destPath = scanner.nextLine();

                // Gửi đường dẫn đến server
                dos.writeUTF(sourcePath);
                dos.writeUTF(destPath);

                // Nhận phản hồi từ server
                String response = dis.readUTF();
                System.out.println("Server: " + response);
            } else {
                System.out.println("Lệnh không hợp lệ.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
