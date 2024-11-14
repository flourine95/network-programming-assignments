package lab7.v1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileUploadClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 2000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server. Type commands:");

            while (true) {
                System.out.print(">");
                String command = scanner.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(command," ");
                command = tokenizer.nextToken();

                if (command.equalsIgnoreCase("EXIT")) {
                    dos.writeUTF(command);
                    break;
                }

                if (command.equalsIgnoreCase("UPLOAD")) {
                    String sourcePath = tokenizer.nextToken();
                    String destPath = tokenizer.nextToken();
                    File file = new File(sourcePath);
                    System.out.println("Source file: " + file.getAbsolutePath());
                    if (!file.exists() || !file.isFile()) {
                        System.out.println("Source file not found.");
                        continue;
                    }

                    dos.writeUTF(command);
                    dos.writeUTF(sourcePath);
                    dos.writeUTF(destPath);

                    try (FileInputStream fis = new FileInputStream(file)) {
                        dos.writeLong(file.length());

                        byte[] buffer = new byte[4096];
                        int read;
                        while ((read = fis.read(buffer)) > 0) {
                            dos.write(buffer, 0, read);
                        }
                    }

                    String response = dis.readUTF();
                    if ("UPLOAD_SUCCESS".equals(response)) {
                        System.out.println("File uploaded successfully.");
                    } else {
                        System.out.println("Failed to upload file: " + response);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
