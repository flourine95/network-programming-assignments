package lab2;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;

public class DirectoryStats {
    private static final String VERTICAL = "│   ";
    private static final String CORNER = "└── ";
    private static final String BRANCH = "├── ";
    private static final String EMPTY = "    ";

    public static void dirStat(String path) {
        File root = new File(path);
        if (!root.exists()) {
            System.out.println("Đường dẫn không tồn tại!");
            return;
        }

        System.out.println(root.getName() + " [" + formatSize(getFolderSize(root)) + "]");
        printDirectoryStats(root, "");
    }

    private static void printDirectoryStats(File folder, String prefix) {
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

            String size = file.isDirectory() ?
                    formatSize(getFolderSize(file)) :
                    formatSize(file.length());

            System.out.println(prefix + connector + file.getName() + " [" + size + "]");

            if (file.isDirectory()) {
                printDirectoryStats(file, childPrefix);
            }
        }
    }

    private static long getFolderSize(File folder) {
        long size = 0;
        File[] files = folder.listFiles();

        if (files == null) return 0;

        for (File file : files) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += getFolderSize(file);
            }
        }

        return size;
    }

    private static String formatSize(long size) {
        if (size <= 0) return "0 B";

        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        digitGroups = Math.min(digitGroups, units.length - 1);

        DecimalFormat df = new DecimalFormat("#,##0.#");
        return df.format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static void main(String[] args) {
        dirStat("D:\\projects\\intelij\\network-programming-assignments\\src\\lab10");
    }
}
