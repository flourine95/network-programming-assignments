package lab8.v2;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class OneConnect extends Thread {
    BufferedReader in;
    PrintWriter out;
    Socket socket;
    double operand1, operand2;
    char operator;

    public OneConnect(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    public void run() {
        String line;
        out.println("Ready...");
        try {
            while (true) {
                // receive request
                // analyze request
                // execute
                // send reponse

                line = in.readLine();
                if ("EXIT".equalsIgnoreCase(line)) break;
                analyzeCommand(line);

                double res = executeCommand();
                out.println(line + " " + res);


            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CaculatorException e) {
            out.println(e.getMessage());
        }

    }

    private void analyzeCommand(String line) throws CaculatorException {
        StringTokenizer tokenizer = new StringTokenizer(line, "+-*/");
        String token1, token2;

        token1 = tokenizer.nextToken();
        // no such element try catch
        token2 = tokenizer.nextToken();
        operator = line.charAt(token1.length());

        try {
            operand1 = Double.parseDouble(token1.trim());
        } catch (NumberFormatException e) {
            throw new CaculatorException("loi roi homie");
        }
        operand2 = Double.parseDouble(token2.trim());
    }

    private double executeCommand() throws CaculatorException {
        double res = 0;
        switch (operator) {
            case '+' -> res = operand1 + operand2;
            case '-' -> res = operand1 - operand2;
            case '*' -> res = operand1 * operand2;
            case '/' -> {
                if (Double.isInfinite(res)) {
                    throw new CaculatorException("loi chia cho 0");
                }
                res = operand1 / operand2;
            }
            default -> throw new RuntimeException();

        }
        return 0;
    }
}
