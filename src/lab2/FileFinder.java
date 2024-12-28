package lab2;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FileFinder {
    public static void main(String[] args) {
        findAll("D:\\projects\\intelij\\network-programming-assignments\\src\\lab2", "java", "txt");
    }

    public static void findAll(String path, String... extensions) {
        File root = new File(path);
        if (!root.exists()) {
            System.out.println("Đường dẫn không tồn tại: " + path);
            return;
        }

        Set<String> extSet = normalizeExtensions(extensions);

        if (extSet.isEmpty()) {
            System.out.println("Không có phần mở rộng nào được chỉ định!");
            return;
        }

        System.out.println("Tìm kiếm các file có phần mở rộng: " + String.join(", ", extSet));
        System.out.println("Trong thư mục: " + root.getAbsolutePath());
        System.out.println("\nKết quả:");

        searchFiles(root, extSet);
    }

    private static Set<String> normalizeExtensions(String... extensions) {
        Set<String> extSet = new HashSet<>();
        for (String ext : extensions) {
            if (ext == null || ext.isBlank()) continue;

            String normalizedExt = ext.trim().toLowerCase();
            if (normalizedExt.startsWith(".")) {
                normalizedExt = normalizedExt.substring(1);
            }
            extSet.add(normalizedExt);
        }
        return extSet;
    }

    private static void searchFiles(File directory, Set<String> extensions) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                searchFiles(file, extensions);
            } else {
                String fileName = file.getName();
                int lastDotIndex = fileName.lastIndexOf('.');

                if (lastDotIndex > 0) {
                    String ext = fileName.substring(lastDotIndex + 1).toLowerCase();
                    if (extensions.contains(ext)) {
                        System.out.println(file.getAbsolutePath());
                    }
                }
            }
        }
    }
}
