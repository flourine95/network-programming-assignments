package lab7.learn;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        int port = 2000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server đang lắng nghe trên cổng " + port);

            while (true) {
                // Chấp nhận kết nối từ client
                Socket socket = serverSocket.accept();
                System.out.println("Kết nối từ: " + socket.getInetAddress());

                // Xử lý yêu cầu từ client
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream())
        ) {
            // Đọc lệnh từ client
            String command = dis.readUTF();
            if ("UPLOAD".equalsIgnoreCase(command)) {
                // Nhận hai đường dẫn từ client
                String sourcePath = dis.readUTF();
                String destPath = dis.readUTF();

                // Xử lý upload
                File sourceFile = new File(sourcePath);
                if (!sourceFile.exists()) {
                    dos.writeUTF("File không tồn tại.");
                    return;
                }

                FileInputStream fis = new FileInputStream(sourceFile);
                FileOutputStream fos = new FileOutputStream(destPath);

                byte[] buffer = new byte[8192];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                // Đóng file streams
                fis.close();
                fos.close();

                dos.writeUTF("Tải lên thành công.");
            } else {
                dos.writeUTF("Lệnh không hợp lệ.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
