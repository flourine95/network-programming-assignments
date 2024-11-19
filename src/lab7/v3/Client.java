package lab7.v3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 2000;

    private BufferedReader userInput;
    private boolean isRunning;
    private Socket socket;
    private String line;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            System.err.println("Cannot connect to server");
        }
        userInput = new BufferedReader(new InputStreamReader(System.in));
        isRunning = true;
    }

    public void run() {
        System.out.println("Client.run");
        while (isRunning) {
            showPrompt();
            try {
                line = userInput.readLine();
                List<String> parts = new ArrayList<>();
                Pattern pattern = Pattern.compile("\"([^\"]*)\"|(\\S+)");
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    if (matcher.group(1) != null) {
                        parts.add(matcher.group(1));
                    } else if (matcher.group(2) != null) {
                        parts.add(matcher.group(2));
                    }
                }
                System.out.println(parts);
                if (line.equals("exit")) {
                    break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showPrompt() {
        System.out.print(">");
    }

    public static void main(String[] args) {
        new Client(HOST, PORT).run();
    }
    /*
     * fis
     * out => up dest
     * flush
     * writelong =>file long
     * flush
     * send data
     * flush
     * fis close
     * sout "in" utf
     * */
}
