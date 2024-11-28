//package lab4;
//
//import java.io.*;
//import java.util.Arrays;
//import java.util.List;
//
//public class Zip {
//
//
//    public void pack(String folder, String packedFile) throws IOException {
//        RandomAccessFile raf = new RandomAccessFile(packedFile, "rw");
//        File dir = new File(folder);
//        List<File> files = Arrays.stream(dir.listFiles(File::isFile)).toList();
//
//        raf.write(files.size());
//        long[] headPos = new long[files.size()];
//        int index = 0;
//        long pos = 0;
//        for (File f : files) {
//            headPos[index++] = raf.getFilePointer();
//            raf.writeLong(pos);
//            raf.writeLong(f.length());
//            raf.writeUTF(f.getName());
//        }
//        index = 0;
//        byte[] buff = new byte[102400];
//        int byteRead = 0;
//        for (File f : files) {
//            pos = raf.getFilePointer();
//            raf.seek(headPos[index++]);
//            raf.writeLong(pos);
//            raf.seek(pos);
//
//            FileInputStream fis = new FileInputStream(f);
//            while ((byteRead = fis.read(buff)) != -1) raf.write(...);
//            fis.close();
//        }
//        raf.close();
//    }
//
//    public void unpack(String packedFile, String extractFile, String destFile) throws IOException {
//        RandomAccessFile raf = new RandomAccessFile(packedFile, "rw");
//        int fileNum = raf.readInt();
//        long pos, len;
//        String name;
//        for (int i = 0; i < fileNum; i++) {
//            pos = raf.readLong();
//            len = raf.readLong();
//            name = raf.readUTF();
//            if (name.equalsIgnoreCase(extractFile)) {
//                raf.seek(pos);
//                FileInputStream fis = new FileInputStream(destFile);
//                dataCopy(raf, fos, len);
//                fis.close();
//                break;
//            }
//
//        }
//        raf.close();
//    }
//}
//
