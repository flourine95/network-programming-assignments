package lab3;

import java.io.*;

public class FileSplitterAndJoiner {
    public static void main(String[] args) throws IOException {
        long time = System.currentTimeMillis();
        new FileSplitterAndJoiner().split("D:\\test\\Chuong01_LinuxOverview-đã gộp.pdf", 1024000);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        new FileSplitterAndJoiner().splitWithBuffer("D:\\test\\Chuong01_LinuxOverview-đã gộp.pdf", 1024000);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }
    public void splitWithBuffer(String source, int pSize) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
        int fileNo = 1;
        while (true) {
            String part = source + "." + generatePartNumber(fileNo++);
            System.out.println("Creating file: " + part);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(part)));
            boolean hasMoreData = dataCopyWithBuffer(bufferedReader, bufferedWriter, pSize);
            bufferedWriter.close();
            if (!hasMoreData) {
                break;
            }
        }
        bufferedReader.close();
    }
    private boolean dataCopyWithBuffer(BufferedReader bufferedReader, BufferedWriter bufferedWriter, int pSize) throws IOException {
        char[] buffer = new char[102400];
        long remain = pSize;
        while (remain > 0) {
            int byteMustRead = remain > buffer.length ? buffer.length : (int) remain;
            int byteRead = bufferedReader.read(buffer);
            if (byteRead == -1) {
                return false;
            }
            bufferedWriter.write(buffer, 0, byteMustRead);
            remain -= byteRead;
        }
        return true;
    }
    public void split(String source, int pSize) throws IOException {
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

    private boolean dataCopy(FileInputStream fis, FileOutputStream fos, int pSize) throws IOException {
        byte[] buffer = new byte[102400];
        long remain = pSize;
        while (remain > 0) {
            int byteMustRead = remain > buffer.length ? buffer.length : (int) remain;
            int byteRead = fis.read(buffer);
            if (byteRead == -1) {
                return false;
            }
            fos.write(buffer, 0, byteMustRead);
            remain -= byteRead;
        }
        return true;
    }

    private String generatePartNumber(int index) {
        if (index < 10) {
            return "00" + index;
        } else if (index < 100) {
            return "0" + index;
        }
        return String.valueOf(index);
    }

}
