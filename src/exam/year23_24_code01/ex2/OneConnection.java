package exam.year23_24_code01.ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;

public class OneConnection implements Runnable {
    private Socket socket;
    private boolean isLogin;
    private boolean isRunning;
    private Dao dao;
    private BufferedReader in;
    private PrintWriter out;


    public OneConnection(Socket socket) throws IOException, SQLException, ClassNotFoundException {
        this.socket = socket;
        this.isLogin = false;
        this.isRunning = true;
        this.dao = new Dao();
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            handleClient();
        } catch (IOException e) {
            out.println(Response.error(e.getMessage()));
        } finally {
            closeResources();
        }

    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }

    private void handleClient() throws IOException {
        out.println("Welcome to the product management system ...");
        String line, command, param, response = "", user = "", pass = "";
        while (!isLogin) {
            line = in.readLine();
            if (line == null || line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+",2);
            command = parts[0].toUpperCase().trim();
            param = parts.length > 1 ? parts[1] : "";
            System.out.println(Arrays.toString(parts));
            System.out.println("[" + command + "] [" + param + "]");
            switch (command) {
                case "USER" -> user = param;
                case "PASS" -> {
                    pass = param;
                    if (auth(user, pass)) {
                        isLogin = true;
                        response = Response.ok("Login successful");
                    } else {
                        response = Response.error("Login failed");
                    }
                }
                case "EXIT" -> {
                    closeResources();
                    return;
                }
                default -> response = Response.error("Invalid command");
            }
            out.println(response);

        }
        while (isRunning && isLogin) {
            line = in.readLine();
            if (line == null || line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            command = parts[0].toUpperCase().trim();
            param = parts.length > 1 ? parts[1] : "";
            switch (command) {
                case "ID" -> response = id(param);
                case "NAME" -> response = dao.delete(param);
                case "RANGE" -> response = dao.range(param);
                case "BUY" -> response = dao.buy(param);
                case "EXIT" -> {
                    closeResources();
                    return;
                }
                default -> response = Response.error("Invalid command");
            }
            out.println(response);
        }
    }

    private String id(String param) {
        try {
            int id = Integer.parseInt(param);
            return dao.get(id).getData().toString();
        } catch (NumberFormatException e) {
            return Response.error("Invalid id");
        }
    }

    private boolean auth(String user, String pass) {
        return "admin".equals(user) && "admin".equals(pass);
//        if (dao.auth(user, pass)) {
//            isLogin = true;
//            out.println("Login successful");
//        } else {
//            out.println("Login failed");
//        }
    }
}
