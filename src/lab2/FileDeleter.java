package lab2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileDeleter {
    public static void main(String[] args) {
        deleteAll("D:\\test - Copy (2)", "java", "txt");
    }
    public static void deleteAll(String path, String... extensions) {
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
        List<File> filesToDelete = new ArrayList<>();
        findFilesToDelete(root, extSet, filesToDelete);
        if (filesToDelete.isEmpty()) {
            System.out.println("Không tìm thấy file nào để xóa.");
            return;
        }

        System.out.println("Các file sẽ bị xóa:");
        for (File file : filesToDelete) {
            System.out.println(file.getAbsolutePath());
        }

        System.out.println("\nTổng cộng " + filesToDelete.size() + " file sẽ bị xóa.");

        int deletedCount = 0;
        int failedCount = 0;

        for (File file : filesToDelete) {
            try {
                if (file.delete()) {
                    deletedCount++;
                } else {
                    failedCount++;
                    System.out.println("Không thể xóa file: " + file.getAbsolutePath());
                }
            } catch (SecurityException e) {
                failedCount++;
                System.out.println("Lỗi khi xóa file " + file.getAbsolutePath() + ": " + e.getMessage());
            }
        }

        System.out.println("\nKết quả xóa file:");
        System.out.println("- Đã xóa thành công: " + deletedCount + " file");
        if (failedCount > 0) {
            System.out.println("- Xóa thất bại: " + failedCount + " file");
        }
    }

    private static Set<String> normalizeExtensions(String... extensions) {
        Set<String> extSet = new HashSet<>();
        for (String ext : extensions) {
            if (ext == null || ext.trim().isEmpty()) continue;

            String normalizedExt = ext.trim().toLowerCase();
            if (normalizedExt.startsWith(".")) {
                normalizedExt = normalizedExt.substring(1);
            }
            extSet.add(normalizedExt);
        }
        return extSet;
    }

    private static void findFilesToDelete(File directory, Set<String> extensions, List<File> filesToDelete) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                findFilesToDelete(file, extensions, filesToDelete);
            } else {
                String fileName = file.getName();
                int lastDotIndex = fileName.lastIndexOf('.');

                if (lastDotIndex > 0) {
                    String ext = fileName.substring(lastDotIndex + 1).toLowerCase();
                    if (extensions.contains(ext)) {
                        filesToDelete.add(file);
                    }
                }
            }
        }
    }
}
