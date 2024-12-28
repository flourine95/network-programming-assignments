package lab2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileCopier {
    public static void main(String[] args) {
        String sourceDir = "D:\\test - Copy (3)";
        String destDir = "D:\\copied";
        copyAll(sourceDir, destDir, "java", "txt");
    }

    public static void copyAll(String sDir, String dDir, String... extensions) {
        File sourceDir = new File(sDir);
        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            System.out.println("Thư mục nguồn không tồn tại hoặc không phải là thư mục: " + sDir);
            return;
        }

        File destDir = new File(dDir);
        if (!destDir.exists()) {
            if (!destDir.mkdirs()) {
                System.out.println("Không thể tạo thư mục đích: " + dDir);
                return;
            }
            System.out.println("Đã tạo thư mục đích: " + dDir);
        }

        Set<String> extSet = normalizeExtensions(extensions);
        if (extSet.isEmpty()) {
            System.out.println("Không có phần mở rộng nào được chỉ định!");
            return;
        }

        List<FileCopyInfo> filesToCopy = new ArrayList<>();
        findFilesToCopy(sourceDir, destDir, sourceDir.getPath(), extSet, filesToCopy);

        if (filesToCopy.isEmpty()) {
            System.out.println("Không tìm thấy file nào để copy.");
            return;
        }

        System.out.println("Các file sẽ được copy:");
        for (FileCopyInfo info : filesToCopy) {
            System.out.println("Từ: " + info.sourceFile.getPath());
            System.out.println("Đến: " + info.destinationFile.getPath());
            System.out.println();
        }

        int successCount = 0;
        int failCount = 0;
        long totalBytes = 0;

        for (FileCopyInfo info : filesToCopy) {
            try {
                File parentDir = info.destinationFile.getParentFile();
                if (!parentDir.exists()) {
                    boolean created = parentDir.mkdirs();
                    if (!created && !parentDir.exists()) {
                        throw new IOException("Không thể tạo thư mục: " + parentDir.getAbsolutePath());
                    }
                }

                Files.copy(info.sourceFile.toPath(), info.destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                successCount++;
                totalBytes += info.sourceFile.length();
            } catch (IOException e) {
                failCount++;
                System.out.println("Lỗi khi copy file " + info.sourceFile.getPath() + ": " + e.getMessage());
            }
        }

        System.out.println("\nKết quả copy file:");
        System.out.println("- Đã copy thành công: " + successCount + " file");
        System.out.println("- Tổng dung lượng: " + formatSize(totalBytes));
        if (failCount > 0) {
            System.out.println("- Copy thất bại: " + failCount + " file");
        }
    }

    private static class FileCopyInfo {
        File sourceFile;
        File destinationFile;

        FileCopyInfo(File source, File destination) {
            this.sourceFile = source;
            this.destinationFile = destination;
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

    private static void findFilesToCopy(File sourceDir, File destDir, String sourcePath, Set<String> extensions, List<FileCopyInfo> filesToCopy) {
        File[] files = sourceDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                findFilesToCopy(file, destDir, sourcePath, extensions, filesToCopy);
            } else {
                String fileName = file.getName();
                int lastDotIndex = fileName.lastIndexOf('.');

                if (lastDotIndex > 0) {
                    String ext = fileName.substring(lastDotIndex + 1).toLowerCase();
                    if (extensions.contains(ext)) {
                        String relativePath = file.getPath().substring(sourcePath.length());
                        File destinationFile = new File(destDir, relativePath);
                        filesToCopy.add(new FileCopyInfo(file, destinationFile));
                    }
                }
            }
        }
    }

    private static String formatSize(long size) {
        if (size <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        digitGroups = Math.min(digitGroups, units.length - 1);

        return String.format("%.1f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }
}
