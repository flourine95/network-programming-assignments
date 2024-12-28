package lab2;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Tree {
    private static final String VERTICAL = "│   ";
    private static final String CORNER = "└── ";
    private static final String BRANCH = "├── ";
    private static final String EMPTY = "    ";

    public static void dirTree(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Đường dẫn không hợp lệ hoặc không phải là thư mục!");
            return;
        }

        System.out.println(folder.getName());
        printDirectoryTree(folder, "");
    }

    private static void printDirectoryTree(File folder, String prefix) {
        File[] files = folder.listFiles();
        if (files == null) return;

        Arrays.sort(files, (f1, f2) -> {
            if (f1.isDirectory() && !f2.isDirectory()) return -1;
            if (!f1.isDirectory() && f2.isDirectory()) return 1;
            return f1.getName().compareToIgnoreCase(f2.getName());
        });

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            boolean isLast = (i == files.length - 1);

            String connector = isLast ? CORNER : BRANCH;
            String childPrefix = prefix + (isLast ? EMPTY : VERTICAL);

            String name = file.getName();
            if (file.isDirectory()) {
                DecimalFormat formatter = new DecimalFormat("#,###");
                String formattedNumber = formatter.format(file.getTotalSpace());
                name = name.toUpperCase() + ": " + formattedNumber + " bytes";
            }

            String line = prefix + connector + name;
            System.out.println(line);

            if (file.isDirectory()) {
                printDirectoryTree(file, childPrefix);
            }
        }
    }

    public static void main(String[] args) {
        String folderPath = "D:\\projects\\intelij\\network-programming-assignments\\src\\lab10";
        dirTree(folderPath);
    }
}
