package lab7.v2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final static int PORT = 2000;
    private ServerSocket server;
    private boolean isRunning;

    public Server(int port) throws IOException {
        server = new ServerSocket(PORT);
        isRunning = true;

    }

    public static void main(String[] args) throws IOException {
        new Server(PORT).run();
    }

    public void run() throws IOException {
        System.out.println("Server open!!!");
        while (isRunning) {
            Socket socket = server.accept();
            System.out.println("Client " + server.getInetAddress().getHostAddress() + " connected");
            new Thread(() -> {
                try (DataInputStream dis = new DataInputStream(socket.getInputStream());
                     DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

                    while (true) {
                        String command = dis.readUTF();
                        switch (command) {
                            case "EXIT" -> System.out.println("Client disconnected.");
                            case "UPLOAD"->{
                                String sourcePath = dis.readUTF();
                                String destPath = dis.readUTF();

                                try (FileOutputStream fos = new FileOutputStream(destPath)) {
                                    long fileSize = dis.readLong();
                                    byte[] buffer = new byte[102400];
                                    int read;
                                    long totalRead = 0;

                                    while ((read = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalRead))) > 0) {
                                        fos.write(buffer, 0, read);
                                        totalRead += read;
                                    }

                                    System.out.println("File uploaded to " + destPath);
                                    dos.writeUTF("SUCCESS");
                                } catch (FileNotFoundException e) {
                                    dos.writeUTF("ERROR: Cannot create destination file.");
                                }

                            }
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
