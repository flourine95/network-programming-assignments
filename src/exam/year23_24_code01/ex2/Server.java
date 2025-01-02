package exam.year23_24_code01.ex2;


import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class Server {
    private ServerSocket serverSocket;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(55555);
    }

    private void run() throws IOException, SQLException, ClassNotFoundException {
        while (true) {
            new Thread(new OneConnection(serverSocket.accept())).start();
        }
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        new Server().run();
    }
}
