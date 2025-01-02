package lab3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Learn {
    public static void split(String path, long pSize) throws IOException {
        try (FileInputStream fis = new FileInputStream(path)) {
            int fileNo = 1;
            while (true) {
                try (FileOutputStream fos = new FileOutputStream(path + "." + generatePartNumber(fileNo))) {
                    if (!dataCopy(fis, fos, pSize)) break;
                }
                fileNo++;
            }
        }
    }

    public static void join(String path) throws IOException {
        String ext = extension(path);
        String origin = path.substring(0, path.lastIndexOf("."));
        try (FileOutputStream fos = new FileOutputStream(newFileName(path, ext))) {
            int fileNo = 1;
            while (true) {
                File file = new File(origin + "." + generatePartNumber(fileNo));
                if (!file.exists()) break;
                try (FileInputStream fis = new FileInputStream(file)) {
                    dataCopy(fis, fos, file.length());
                }
                fileNo++;
            }
        }
    }

    private static boolean dataCopy(FileInputStream fis, FileOutputStream fos, long pSize) throws IOException {
        byte[] buff = new byte[8096];
        long remain = pSize;
        while (remain > 0) {
            int byteMustRead = (int) Math.min(buff.length, remain);
            int read = fis.read(buff, 0, byteMustRead);
            if (read == -1) return false;
            fos.write(buff, 0, read);
            remain -= read;
        }
        return true;
    }

    public static String generatePartNumber(int fileNum) {
        return String.format("%03d", fileNum);
    }

    private static String extension(String path) {
        int last = path.lastIndexOf(".");
        int beforeLast = path.lastIndexOf(".", last - 1);
        return path.substring(beforeLast + 1, last);
    }

    private static String newFileName(String path, String ext) {
        int lastDot = path.lastIndexOf(".");
        if (lastDot == -1) return path + ".joined";
        String origin = path.substring(0, lastDot);
        return origin + ".joined." + ext;
    }

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\flourine\\Downloads\\test\\Lab_8_Sentiment_Analysis_22130152_NguyenPhiLong.pdf";
//        split(path, 1024 * 1024);
        join(path + ".001");
    }
}
