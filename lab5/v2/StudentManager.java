package lab5.v2;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class StudentManager {
    private int nLen;
    private RandomAccessFile raf;
    private int quantityStudent;
    private int recordSize;
    private int NAME_LENGTH = 25;

    public StudentManager(String file) throws IOException {
        raf = new RandomAccessFile(file, "rw");
        if (raf.length() > 0) {
            quantityStudent = raf.readInt();
            recordSize = raf.readInt();
            nLen = (recordSize - 4 - 4 - 8) / 2;
        } else {
            // writeUTF can't read length
            nLen = NAME_LENGTH;
            quantityStudent = 0;
            recordSize = 4 + 4 + 8 + 2 * nLen;
            raf.writeInt(quantityStudent);
            raf.writeInt(recordSize);
        }
    }

    public void addStudent(Student st) throws IOException {
        raf.seek(raf.length());
        st.save(raf, nLen);
        quantityStudent++;
        raf.seek(0);
        raf.writeInt(quantityStudent);
    }

    public Student getStudent(int index) throws IOException {
        long pos = 4 + 4 + index * recordSize;
        raf.seek(pos);
        Student student = new Student();
        student.load(raf, nLen);
        return student;
    }

    public void updateStudent(int index, Student newSt) throws IOException {
        long pos = 4 + 4 + index * recordSize;
        raf.seek(pos);
        newSt.save(raf, nLen);
    }

    public Student findById(int id) {

        return null;
    }

    public void close() throws IOException {
        raf.close();
    }
}
