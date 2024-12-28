package lab2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PatternFinder {
    public static void main(String[] args) {
        String[] testPatterns = {
                "Cmd*.java",
                "Student.java",
                "test*.txt",
                "*.java",
                "readme.*",
                "document.txt"
        };
//        List<String> results = findAll("D:\\projects\\intelij\\network-programming-assignments\\src", "*.java");
//        List<String> results = findAll("D:\\projects\\intelij\\network-programming-assignments\\src", testPatterns[0]);
        List<String> results = findAll("D:\\projects\\intelij\\network-programming-assignments\\src", testPatterns[1]);
        printResults(results, testPatterns[1]);
    }

    public static List<String> findAll(String path, String pattern) {
        List<String> results = new ArrayList<>();

        File root = new File(path);
        if (!root.exists()) {
            System.out.println("Đường dẫn không tồn tại: " + path);
            return results;
        }

        if (!isValidPattern(pattern)) {
            System.out.println("Pattern không hợp lệ! Pattern chỉ được chứa tối đa một dấu *");
            return results;
        }

        String regex = convertPatternToRegex(pattern);

        searchFiles(root, regex, results);

        return results;
    }

    private static boolean isValidPattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return false;
        }
        int starCount = pattern.length() - pattern.replace("*", "").length();
        return starCount <= 1;
    }

    private static String convertPatternToRegex(String pattern) {
        String escaped = pattern
                .replace(".", "\\.")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("$", "\\$")
                .replace("^", "\\^")
                .replace("+", "\\+")
                .replace("|", "\\|")
                .replace("?", "\\?");

        return escaped.replace("*", ".*");
    }

    private static void searchFiles(File directory, String regex, List<String> results) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                searchFiles(file, regex, results);
            } else {
                if (file.getName().matches(regex)) {
                    results.add(file.getAbsolutePath());
                }
            }
        }
    }

    private static void printResults(List<String> results, String pattern) {
        System.out.println("Kết quả tìm kiếm cho pattern: " + pattern);
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy file nào phù hợp.");
        } else {
            System.out.println("Tìm thấy " + results.size() + " file:");
            for (String path : results) {
                System.out.println(path);
            }
        }
    }
}
