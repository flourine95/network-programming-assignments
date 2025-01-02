package lab11.v1.client;

import lab11.v1.services.StudentService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 2000);

            StudentService server = (StudentService) registry.lookup("StudentService");
            Scanner scanner = new Scanner(System.in);

            String username = "";
            String password;
            boolean loggedIn = false;

            while (!loggedIn) {
                String console = scanner.nextLine();
                String[] parts = console.trim().split("\\s+");
                String command = parts[0].trim();
                String param = "";
                if (parts.length == 2) {
                    param = parts[1].trim();
                }
                switch (command.toUpperCase()) {
                    case "USERNAME" -> username = param;
                    case "PASSWORD" -> {
                        password = param;
                        String response = server.login(username, password);
                        System.out.println(response);
                        if (response.equals("Login successful")) {
                            loggedIn = true;
                        }
                    }
                    case "QUIT" -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid command");
                }
            }

            while (loggedIn) {
                String command = scanner.nextLine();
                switch (command.toUpperCase()) {
                    case "FINDBYID" -> server.findById(Integer.parseInt(scanner.nextLine()));
                    case "FINDBYNAME" -> server.findByName(scanner.nextLine());
                    case "QUIT" -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid command");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
