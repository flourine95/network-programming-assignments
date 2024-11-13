package lab1;

import java.io.File;

public class FileHelper {

    public static void main(String[] args) {
        String path = "D:\\test - Copy";

        System.out.println(delete(path, false));
    }

    public static boolean delete(String path, boolean option) {
        File file = new File(path);
        return option ? deleteAll(file) : deleteFilesOnly(file);
    }

    private static boolean deleteAll(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    deleteAll(subFile);
                }
            }
        }
        return file.delete();
    }

    private static boolean deleteFilesOnly(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    deleteFilesOnly(subFile);
                }
            }
        } else {
            return file.delete();
        }
        return true;
    }
}
