package lab9;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static final int PORT = 54321;
    private ServerSocket serverSocket;
    private boolean isRunning;
   

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.isRunning = true;
    }

    public void run() {
        System.out.println("Server is running on port " + PORT);
        while (isRunning) {
            try {
                new OneConnect(serverSocket.accept()).run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        new Server(PORT).run();
    }
}
