package lab9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class OneConnect implements Runnable {
    private static final int PORT = 54321;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    public String validUsername = "admin";
    public String validPassword = "admin";
    private StudentManager studentManager;
    private boolean isLoggedIn = false;

    public OneConnect(Socket socket) {
        this.socket = socket;
        this.studentManager = new StudentManager();
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        out.println("220 Server ready");

        String line;
        while (true) {
            try {
                if ((line = in.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String response = processCommand(line);
            out.println(response);

            if (response.startsWith("221")) {
                break;
            }
        }

    }

    private String processCommand(String command) {
        String[] parts = command.split("\\s+", 2);
        String cmd = parts[0].toUpperCase();

        // Kiểm tra trạng thái đăng nhập
        if (!isLoggedIn) {
            return switch (cmd) {
                case "USER" -> parts.length > 1 && parts[1].equals(validUsername)
                        ? "331 User name okay"
                        : "530 User not valid";
                case "PASS" -> {
                    if (parts.length > 1 && parts[1].equals(validPassword)) {
                        isLoggedIn = true;
                        yield "230 Logged in successfully";
                    }
                    yield "530 Password incorrect";
                }
                case "QUIT" -> "221 Goodbye";
                default -> "530 Please login first";
            };
        }

        // Các lệnh sau khi đăng nhập
        switch (cmd) {
            case "FINDBYID":
                if (parts.length < 2) return "500 Syntax error";
                List<Student> studentsById = studentManager.findById(parts[1]);
                return studentsById.isEmpty()
                        ? "Không tìm thấy"
                        : formatStudentResults(studentsById);

            case "FINDBYNAME":
                if (parts.length < 2) return "500 Syntax error";
                List<Student> studentsByName = studentManager.findByName(parts[1]);
                return studentsByName.isEmpty()
                        ? "Không tìm thấy"
                        : formatStudentResults(studentsByName);

            case "QUIT":
                return "221 Goodbye";

            default:
                return "500 Unknown command";
        }
    }
    private String formatStudentResults(List<Student> students) {
        StringBuilder result = new StringBuilder();
        for (Student student : students) {
            result.append(student.toString()).append("\n");
        }
        return result.toString().trim();
    }

}
