package lab10;

import java.io.*;
import java.net.*;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader console;

    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.console = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {
        while (true) {
            String command = console.readLine();
            if (command != null) {
                out.println(command);
            }
            String response = in.readLine();
            if (response != null) {
                System.out.println(response);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Client(HOST, PORT).run();
    }
}

