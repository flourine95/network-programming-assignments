package lab9.v2;


import lab9.v2.dao.StudentDAO;
import lab9.v2.dao.UserDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static lab9.v2.helpers.Helper.format;

public class OneConnect implements Runnable {
    private static final int PORT = 54321;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private StudentDAO students;
    private UserDAO users;
    private boolean isLoggedIn;
    private boolean isRunning;

    public OneConnect(Socket socket) {
        this.socket = socket;
        this.students = new StudentDAO();
        this.users = new UserDAO();
        isLoggedIn = false;
        isRunning = true;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Connection successful !!");
            out.println("Please login!!!");

            String line;
            String command;
            String param = "";
            String user = "";
            String pass;
            while (!isLoggedIn) {
                line = in.readLine();
                String[] parts = line.trim().split("\\s+", 2);
                command = parts[0].trim();
                if (parts.length == 2) {
                    param = parts[1].trim();
                }
                switch (command.toUpperCase()) {
                    case "USER" -> user = param;
                    case "PASS" -> {
                        pass = param;
                        if (users.isValidLogin(user, pass)) {
                            out.println("Login successful!");
                            isLoggedIn = true;
                        } else {
                            out.println("Invalid username or password. Please try again.");
                            out.println("USER <username>");
                        }
                    }
                    case "QUIT" -> {
                        isRunning = false;
                        socket.close();
                        out.println("Goodbye1!");
                    }
                    default -> out.println("Invalid command: " + line);
                }
            }
            while (isRunning) {
                line = in.readLine();
                String[] parts = line.trim().split("\\s+", 2);
                command = parts[0].trim();
                if (parts.length == 2) {
                    param = parts[1].trim();
                }
                switch (command.toUpperCase()) {
                    case "FBID" -> out.println(students.findById(param));
                    case "FBN" -> out.println(format(students.findByName(param)));
                    case "QUIT" -> {
                        isRunning = false;
                        socket.close();
                        out.println("Goodbye2!");
                    }
                    default -> System.err.println("Invalid command: " + line);
                }

            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
