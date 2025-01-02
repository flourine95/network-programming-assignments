package exam.year23_24_code07.ex2;


import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class Server {
    private static final int PORT = 1080;
    private ServerSocket serverSocket;
    private boolean isRunning;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.isRunning = true;
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        new Server().run();
    }

    private void run() throws IOException, SQLException, ClassNotFoundException {
        while (isRunning) {
            new Thread(new OneConnection(serverSocket.accept())).start();
        }
    }

}
