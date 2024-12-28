package lab1;

import java.io.File;

public class FileHelper {

    public static void main(String[] args) {
        String path = "D:\\test - Copy";
        System.out.println(delete(path, true));
    }

    public static boolean delete(String path, boolean option) {
        File file = new File(path);
        return option ? deleteAll(file) : deleteFilesOnly(file);
    }

    private static boolean deleteAll(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteAll(file);
                }
            }
        }
        return f.delete();
    }

    private static boolean deleteFilesOnly(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFilesOnly(file);
                }
            }
        } else {
            return f.delete();
        }
        return true;
    }
}
