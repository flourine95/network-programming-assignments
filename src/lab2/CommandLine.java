package lab2;

import lab1.FileHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CommandLine {
    private File defaultDirectory;
    private BufferedReader reader;
    private boolean isRunning = true;
    private String command;

    public CommandLine(String defaultDirectory) {
        this.defaultDirectory = new File(defaultDirectory);
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        while (isRunning) {
            showPrompt();
            getUserInput();
            processCommand();

        }
    }

    private void processCommand() {
        String[] params = command.trim().split(" ", 2);
        switch (params[0].toLowerCase()) {
            case "exit" -> isRunning = false;
            case "cd" -> {
                if (params.length < 2) {
                    System.out.println(defaultDirectory.getAbsolutePath());
                    return;
                }
                changeDirectory(params[1]);
            }
            case "dir" -> listDirectory();
            case "delete" -> {
                if (params.length < 2) {
                    System.out.println("Invalid command: delete");
                    return;
                }
                delete(params[1]);
            }
            default -> System.out.println("Command not found: " + command);
        }
    }

    private void changeDirectory(String param) {
        if (param.equals("..")) {
            if (defaultDirectory.getParent() != null) {
                defaultDirectory = defaultDirectory.getParentFile();
            }
        } else {
            File newDir = new File(defaultDirectory, param);

            if (newDir.exists() && newDir.isDirectory()) {
                defaultDirectory = newDir;
            } else {
                System.out.println("Directory not found: " + param);
            }
        }
    }

    private void delete(String fileName) {
        fileName = fileName.replace("\"", "").trim();
        File file = new File(defaultDirectory + File.separator + fileName);
        if (file.exists()) {
            FileHelper.delete(file.getAbsolutePath(), true);
        } else {
            System.out.println("File not found: " + file.getName());
        }

    }

    private void listDirectory() {
        File[] files = defaultDirectory.listFiles();
        if (files != null) {
            Arrays.sort(files, (f1, f2) -> {
                if (f1.isDirectory() && !f2.isDirectory()) {
                    return -1;
                } else if (!f1.isDirectory() && f2.isDirectory()) {
                    return 1;
                } else {
                    return f1.getName().compareTo(f2.getName());
                }
            });
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(file.getName().toUpperCase());
                } else {
                    System.out.println(file.getName());
                }
            }
        }

    }

    private void getUserInput() {
        try {
            command = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void showPrompt() {
        System.out.print(defaultDirectory + "> ");
    }

    public static void main(String[] args) {
        new CommandLine("D:\\test").run();
    }

}
