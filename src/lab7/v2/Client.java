package lab7.v2;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    private static final int PORT = 2000;
    private static final String HOST = "localhost";
    private Socket socket;
    private String prompt;
    private boolean isRunning;
    private BufferedReader userInput;

    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        userInput = new BufferedReader(new InputStreamReader(System.in));
        isRunning = true;
    }

    public void run() throws IOException {
        while (isRunning) {
            showPrompt();
            getUserPrompt();
            process();
        }
    }

    private void process() throws IOException {
        String[] parts = prompt.trim().split("\\s+");
        System.out.println(Arrays.toString(parts));
        switch (parts[0].trim().toUpperCase()) {
            case "EXIT" -> exit();
            case "UPLOAD" -> upload(parts[1], parts[2]);
            default -> System.err.println("There is no such command");
        }
    }
    private void exit() throws IOException {
        isRunning = false;
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
            dos.writeUTF("EXIT");
        }
    }

    private void upload(String source, String dest) throws IOException {
        File sourceFile = new File(source);
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            System.out.println(source + " is not a file or not exist.");
            return;
        }
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        dos.writeUTF("UPLOAD");
        dos.writeUTF(source);
        dos.writeUTF(dest);

        try (FileInputStream fis = new FileInputStream(sourceFile)) {
            dos.writeLong(sourceFile.length());
            byte[] buff = new byte[102400];
            int read;
            while ((read = fis.read(buff)) > 0) {
                dos.write(buff, 0, read);
            }
        }

        String response = dis.readUTF();
        if ("SUCCESS".equals(response)) {
            System.out.println("Upload OK!");
        } else {
            System.out.println("Error " + response);
        }
        dis.close();
        dos.close();
    }

    private void getUserPrompt() throws IOException {
        prompt = userInput.readLine();
    }

    private void showPrompt() {
        System.out.print(">");
    }

    public static void main(String[] args) throws IOException {
        new Client(HOST, PORT).run();
    }

}
