package lab9.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 54321;
    private boolean isRunning;
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

    private class ServerReader implements Runnable {
        @Override
        public void run() {
            try {
                BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response;
                while ((response = serverIn.readLine()) != null) {
                    System.out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ConsoleWriter implements Runnable {
        @Override
        public void run() {
            try {
                BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
                while (isRunning) {
                    String line = consoleIn.readLine();
                    if (line != null) {
                        out.println(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void run() throws IOException {
        System.out.println("Client connected: " + socket.getInetAddress());
        out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(new ServerReader()).start();
        new Thread(new ConsoleWriter()).start();
    }

    public static void main(String[] args) {
        try {
            new Client(HOST, PORT).run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
