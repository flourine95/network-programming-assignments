package lab7.v3;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static final int PORT = 2000;
    private ServerSocket serverSocket;
    private boolean isRunning;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            isRunning = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        while (isRunning) {
            try {
                new Thread(new OneConnect(serverSocket.accept())).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        new Server(PORT).run();
    }

}
