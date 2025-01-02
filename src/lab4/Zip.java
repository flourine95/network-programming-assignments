package lab4;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class Zip {

    public static void main(String[] args) throws IOException {
        String folder = "D:\\test\\zip";
        String extractFile = "D:\\test\\zip\\test.pack";
//        pack(folder, extractFile);
        unpack(extractFile, "123.txt", "D:\\test\\zip\\123.unpack");
    }


    public static void pack(String folder, String packedFile) throws IOException {
        List<File> files = List.of(Objects.requireNonNull(new File(folder).listFiles()));
        try (RandomAccessFile raf = new RandomAccessFile(packedFile, "rw")) {
            raf.writeInt(files.size());
            long[] headPos = new long[files.size()];
            int index = 0;
            for (File f : files) {
                headPos[index++] = raf.getFilePointer();
                raf.writeLong(0);
                raf.writeLong(f.length());
                raf.writeUTF(f.getName());
            }
            index = 0;
            byte[] buff = new byte[102400];
            int byteRead;
            for (File f : files) {
                long pos = raf.getFilePointer();
                raf.seek(headPos[index++]);
                raf.writeLong(pos);
                raf.seek(pos);
                try (FileInputStream fis = new FileInputStream(f)) {
                    while ((byteRead = fis.read(buff)) != -1) {
                        raf.write(buff, 0, byteRead);
                    }
                }
            }
        }
    }

    public static void unpack(String packedFile, String extractFile, String destFile) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(packedFile, "rw")) {
            int fileNum = raf.readInt();
            long pos, len;
            String name;
            for (int i = 0; i < fileNum; i++) {
                pos = raf.readLong();
                len = raf.readLong();
                name = raf.readUTF();
                if (name.equalsIgnoreCase(extractFile)) {
                    raf.seek(pos);
                    try (FileOutputStream fos = new FileOutputStream(destFile)) {
                        dataCopy(raf, fos, len);
                    }
                    break;
                }

            }
        }
    }

    private static void dataCopy(RandomAccessFile raf, FileOutputStream fos, long len) throws IOException {
        byte[] buff = new byte[102400];
        int byteRead;
        while (len > 0) {
            byteRead = raf.read(buff, 0, (int) Math.min(len, buff.length));
            fos.write(buff, 0, byteRead);
            len -= byteRead;
        }
    }
}

