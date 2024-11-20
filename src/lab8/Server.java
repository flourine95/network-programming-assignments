package lab8;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    ServerSocket serverSocket;
    boolean isRunning;
    String line;

    public Server() {
        try {
            serverSocket = new ServerSocket(12345);
            isRunning = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            new Server().run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        new Server().test();
    }

    void test() {
        line = "        asd asd1 as asd as  ";
        String[] parts = line.trim().split("\\s+");
        System.out.println(Arrays.toString(parts));
    }

    public void run() throws IOException {
        System.out.println("Server open in port 12345");

        while (isRunning) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connect from: " + clientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true);


            while ((line = in.readLine()) != null) {
                if (line.equalsIgnoreCase("EXIT")) {
                    out.println("Close connect!");
                    break;
                }

                String response = handleCalculation();
                out.println(response);
            }

            clientSocket.close();
        }

    }

//    private String split() {
//        List<String> strings = new ArrayList<>();
//        for (char c : line.trim().toCharArray()) {
//            String number = "";
//            if (Character.isDigit(c)) {
//                strings.add(number)
//            }
//        }
//        return "";
//    }

    String handleCalculation() {
        try {

            String[] parts = line.split("\\s+");
            if (parts.length != 3) {

                return "Error: command not valid";
            }

            double operand1 = Double.parseDouble(parts[0].trim());
            String operator = parts[1].trim();
            double operand2 = Double.parseDouble(parts[2].trim());

            double result = 0;
            switch (operator) {
                case "+" -> result = operand1 + operand2;
                case "-" -> result = operand1 - operand2;
                case "*" -> result = operand1 * operand2;
                case "/" -> {
                    // khong nen
                    if (operand2 == 0) {
                        return "Error: divide by 0";
                    }
                    result = operand1 / operand2;
                }
                default -> {
                    return "Error: invalid operator";
                }
            }

            return String.format("%.0f", operand1)
                    + operator
                    + String.format("%.0f", operand2)
                    + "="
                    + String.format("%.0f", result);
        } catch (NumberFormatException e) {
            return "Error: NumberFormatException";
        } catch (Exception e) {
            return "Error: Exception";
        }
    }
}

