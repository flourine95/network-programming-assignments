package lab7.v1;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            while (true) {
                String command = dis.readUTF();

                if (command.equalsIgnoreCase("EXIT")) {
                    System.out.println("Client disconnected.");
                    break;
                }

                if (command.startsWith("UPLOAD")) {
                    String sourcePath = dis.readUTF();
                    String destPath = dis.readUTF();

                    try (FileOutputStream fos = new FileOutputStream(destPath)) {
                        long fileSize = dis.readLong();
                        byte[] buffer = new byte[4096];
                        int read;
                        long totalRead = 0;

                        while ((read = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalRead))) > 0) {
                            fos.write(buffer, 0, read);
                            totalRead += read;
                        }

                        System.out.println("File uploaded to " + destPath);
                        dos.writeUTF("UPLOAD_SUCCESS");
                    } catch (FileNotFoundException e) {
                        dos.writeUTF("ERROR: Cannot create destination file.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
