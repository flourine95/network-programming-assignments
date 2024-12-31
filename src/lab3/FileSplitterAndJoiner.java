package lab3;

import java.io.*;
import java.util.Arrays;

public class FileSplitterAndJoiner {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\flourine\\Downloads\\test\\Lab_8_Sentiment_Analysis_22130152_NguyenPhiLong.pdf";
//        split(path, 1024 * 1024);
        join(path + ".001");
    }

    public static void join(String partFilename) throws IOException {
        String ext = getFileExtension(partFilename);
        String dest = partFilename.substring(0, partFilename.lastIndexOf("."));
        FileOutputStream fos = new FileOutputStream(dest + "joined." + ext);
        int fileNum = 1;
        while (true) {
            String src = dest +"." + generatePartNumber(fileNum);
            System.out.println("Joining file: " + src);
            File file = new File(src);
            if (!file.exists()) {
                break;
            }
            FileInputStream fis = new FileInputStream(file);
            dataCopy(fis, fos, file.length());
            fis.close();
            fileNum++;
        }
        fos.flush();
        fos.close();
    }

    public static void split(String source, int pSize) throws IOException {
        FileInputStream fis = new FileInputStream(source);
        int fileNo = 1;
        while (true) {
            String part = source + "." + generatePartNumber(fileNo++);
            System.out.println("Creating file: " + part);
            FileOutputStream fos = new FileOutputStream(part);
            boolean hasMoreData = dataCopy(fis, fos, pSize);
            fos.close();
            if (!hasMoreData) {
                break;
            }
        }
        fis.close();

    }

    private static String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");

        int secondLastDotIndex = filename.lastIndexOf(".", lastDotIndex - 1);

        if (secondLastDotIndex != -1) {
            return filename.substring(secondLastDotIndex + 1, lastDotIndex);
        }
        return "";
    }



    private static boolean dataCopy(FileInputStream input, FileOutputStream output, long pSize) throws IOException {
        byte[] buff = new byte[1024*1024];
        long remain = pSize;

        while (remain > 0) {
            int byteMustRead = (int) (remain > buff.length ? buff.length : remain);
            int byteread = input.read(buff, 0, byteMustRead);
            if (byteread == -1) return false;
            output.write(buff, 0, byteread);
            remain -= byteread;
        }

        return true;

    }

    private static String generatePartNumber(int index) {
        if (index < 10) {
            return "00" + index;
        } else if (index < 100) {
            return "0" + index;
        }
        return String.valueOf(index);
    }

}
