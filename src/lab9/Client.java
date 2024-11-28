package lab9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 54321;
    private final boolean isRunning;
    private BufferedReader in;
    private PrintWriter out;

    private final Socket socket;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            isRunning = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void run() {
        System.out.println("Client connected: " + socket.getInetAddress());
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(in.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String line;
        while (true) {
            try {
                if ((line = stdIn.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.println(line);

            // Đọc phản hồi từ server
            String response = null;
            try {
                response = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(response);

            // Thoát nếu server gửi thông báo kết thúc
            if (response.startsWith("221")) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Client(HOST, PORT).run();
    }

}
