import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "D:\\flo\\abc.txt";
        split2(path, 5);
    }

    public static void split(String path, long psize) {
        try {
            File inputFile = new File(path);

            if (!inputFile.exists()) {
                throw new FileNotFoundException("File không tồn tại: " + path);
            }

            String fullFileName = inputFile.getName();

            byte[] buffer = new byte[100000];

            try (FileInputStream fis = new FileInputStream(inputFile)) {
                int index = 1;
                long bytesRemaining = inputFile.length();

                while (bytesRemaining > 0) {
                    String partFileName = fullFileName + "." + generatePartNumber(index++);
                    File outputFile = new File(inputFile.getParent(), partFileName);

                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        long currentPartSize = Math.min(psize, bytesRemaining);
                        long bytesWritten = 0;

                        while (bytesWritten < currentPartSize) {
                            int len = (int) Math.min(buffer.length, currentPartSize - bytesWritten);
                            int bytesRead = fis.read(buffer, 0, len);

                            if (bytesRead == -1) {
                                break;
                            }

                            fos.write(buffer, 0, bytesRead);
                            bytesWritten += bytesRead;
                            bytesRemaining -= bytesRead;
                        }
                    }
                }
            }

            System.out.println("Done!");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void split2(String path, long psize) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        int fileNo = 1;

        while (true) {
            String dest = path + "." + generatePartNumber(fileNo);
            FileOutputStream fos = new FileOutputStream(dest);
            boolean hasMoreData = dataCoppy(fis, fos, psize);

            fos.close();
            fileNo++;
            if (!hasMoreData) break;
        }
        fis.close();
    }

    private static boolean dataCoppy(FileInputStream fis, FileOutputStream fos, long psize) throws IOException {
        byte[] buff = new byte[102400];
        long remain = psize;
        while (remain > 0) {
            int byteMustRead = remain > buff.length ? buff.length : (int) remain;
            int byteRead = fis.read(buff);
            if (byteRead == -1) {
                return false;
            }
            fos.write(buff, 0, byteMustRead);
            remain -= byteRead;
        }
        return true;
    }

    public static void join(String path) throws IOException {
        String dest = path.substring(0, path.lastIndexOf('.'));
        FileOutputStream fos = new FileOutputStream(dest);
        int fileNo = 1;
        while (true) {
            String source = dest + "." + generatePartNumber(fileNo);
            File file = new File(source);
            if (!file.exists()) break;
            FileInputStream fis = new FileInputStream(source);
            dataCoppy(fis, fos, file.length());
            fileNo++;
        }
        fos.close();
    }


    private static String generatePartNumber(int number) {
        if (number < 0) return "000";

        String num = String.valueOf(number);
        return "0".repeat(Math.max(0, 3 - num.length())) + num;
    }
}
