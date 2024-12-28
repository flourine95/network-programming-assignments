package lab2;


import lab1.FileHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandLine {
    private String defaultDir;
    private boolean isRunning;
    private BufferedReader input;
    private String line;

    public CommandLine() {
        this.defaultDir = System.getProperty("user.home");
        this.isRunning = true;
        this.input = new BufferedReader(new InputStreamReader(System.in));
    }


    public static void main(String[] args) throws IOException {
        new CommandLine().run();
    }

    public void run() throws IOException {
        while (isRunning) {
            showPrompt();
            getInput();
            processCommand();
        }
    }

    private void processCommand() {
        String[] tokens = line.trim().split(" ", 2);
        String command = tokens[0].trim();
        String arg = tokens.length > 1 ? tokens[1].trim() : "";
        switch (command.toLowerCase()) {
            case "exit" -> exit();
            case "cd", "cd.." -> changeDir(arg);
            case "pwd" -> printWorkingDirectory();
            case "dir" -> directory();
            case "delete" -> delete();
            default -> logError(command);
        }

    }

    private void logError(String command) {
        System.out.println("'" + command + "'" + " is not recognized as an internal or external command, operable program or batch file.");
    }

    private void delete() {
        FileHelper.delete(defaultDir, true);
    }

    private void exit() {
        isRunning = false;
    }

    private void printWorkingDirectory() {
        System.out.println(defaultDir);
    }

    private void directory() {
        File folder = new File(defaultDir);
        File[] files = folder.listFiles();
        if (files == null) {
            System.out.println("Không thể truy cập thư mục.");
            return;
        }
        System.out.println("\nDirectory of " + defaultDir);
        int countFiles = 0;
        int countDirs = 0;
        for (File file : files) {
            if (file.isDirectory()) {
                countDirs++;
            } else {
                countFiles++;
            }
            String type = file.isDirectory() ? "<DIR>" : "     ";
            String dateModified = new SimpleDateFormat("dd/MM/yyyy  hh:mm a").format(new Date(file.lastModified()));
            long size = file.isDirectory() ? 0 : file.length();

            System.out.printf("%20s %5s %10s %s%n", dateModified, type, size, file.getName());
        }

        long totalSpace = folder.getTotalSpace();
        long freeSpace = folder.getFreeSpace();
        System.out.printf("\n%d File(s)    %d bytes%n", countFiles, totalSpace - freeSpace);
        System.out.printf("%d Dir(s)     %d bytes free%n", countDirs, freeSpace);
    }

    private void changeDir(String arg) {
        if (arg == null || arg.isBlank() || "cd..".equals(line.trim()) || ".".equals(arg)) {
            printWorkingDirectory();
            return;
        }
        if ("..".equals(arg)) {
            File parent = new File(defaultDir).getParentFile();
            if (parent != null) {
                defaultDir = parent.getAbsolutePath();
            }
            return;
        }
        File newDir = new File(arg);
        if (!newDir.isAbsolute()) {
            newDir = new File(defaultDir, arg);
        }
        if (!newDir.exists() || !newDir.isDirectory()) {
            System.out.println("The system cannot find the path specified.");
            return;
        }
        defaultDir = newDir.getAbsolutePath();
    }

    private void getInput() throws IOException {
        line = input.readLine();
    }

    private void showPrompt() {
        System.out.print(defaultDir + "> ");
    }
}
