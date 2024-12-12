package lab10;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {
    private static final Path BASE_PATH = Paths.get(System.getProperty("user.dir"));
    private static final String DB_NAME = "database.accdb";
    private static final String DB_URL = "jdbc:ucanaccess://" + BASE_PATH.resolve(DB_NAME);
    private static final int PORT = 12345;
    private static final String HOST = "localhost";
    private ServerSocket serverSocket;
    private boolean isRunning;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.isRunning = true;
    }

    public void run() throws IOException {
        while (isRunning) {
            new Thread(new OneConnection(serverSocket.accept())).start();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(PORT).run();
    }

}
