import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class CommandLine {
    private File defaultDir;
    private boolean isExit = false;
    private BufferedReader userInput;

    public CommandLine(String path) {
        this.defaultDir = new File(path);
        this.userInput = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        String line, res;

        System.out.print(getPrompt());

        while (!isExit) {
            line = getUserCommand();
            res = executeCommand(line);
            System.out.println(res);
            System.out.print(getPrompt());
        }

    }

    private String executeCommand(String line) {
        String res = "";
        if (line.isBlank()) return "";
        String command, param = "";
        StringTokenizer st = new StringTokenizer(line);
        command = st.nextToken().toUpperCase();
        if (st.hasMoreTokens()) {
            param = st.nextToken();
        }
        switch (command) {
            case "EXIT":
                isExit = true;
                break;
            case "DIR":
                break;
            case "CD":
                res = changeDir(param);
                break;
        }
        return res;
    }

    private String changeDir(String param) {
        String res = "";
        if (param.isBlank()) return defaultDir.getAbsolutePath();
        if ("..".equals(param)) {
            File temp = defaultDir.getParentFile();
            if (temp != null) {
                defaultDir = temp;
            }
        } else {
            File temp = new File(defaultDir, param);
            if (temp.exists()) defaultDir = temp;
            else res = "Folder: " + param + " not exists";
        }
        return res;
    }

    private String getUserCommand() {
        try {
            return userInput.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPrompt() {
        return defaultDir.getAbsolutePath() + ">";
    }

    public static void main(String[] args) {
        new CommandLine(Paths.get("").toAbsolutePath() + "").run();
    }
}
