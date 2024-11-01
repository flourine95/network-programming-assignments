package lab5.v1;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StudentManager {
    private static final String FILE_NAME = "lab5/v1/students.dat";
    private RandomAccessFile raf;
    private int studentCount;
    private final int recordSize;

    public StudentManager() throws IOException {
        raf = new RandomAccessFile(FILE_NAME, "rw");
        recordSize = 4 + 4 + 4 + 8;

        if (raf.length() == 0) {
            raf.writeInt(0);
            raf.writeInt(recordSize);
        } else {
            raf.seek(0);
            studentCount = raf.readInt();
        }
    }

    public void addStudent(Student st) throws IOException {
        raf.seek(0);
        studentCount++;
        raf.writeInt(studentCount);
        raf.seek(raf.length());
        raf.write(st.toByteArray());
    }

    public Student getStudent(int index) throws IOException {
        if (index < 0 || index >= studentCount) {
            throw new IndexOutOfBoundsException("Invalid student index");
        }
        long pos = 4 + 4 + index * recordSize;
        raf.seek(pos);
        byte[] studentBytes = new byte[recordSize];
        raf.readFully(studentBytes);
        return Student.fromByteArray(studentBytes);
    }

    public void updateStudent(int index, Student newSt) throws IOException {
        if (index < 0 || index >= studentCount) {
            throw new IndexOutOfBoundsException("Invalid student index");
        }
        long pos = 4 + 4 + index * recordSize; // 4 (count) + 4 (record size)
        raf.seek(pos);
        raf.write(newSt.toByteArray()); // Ghi đè dữ liệu mới
    }

    public Student findById(int id) throws IOException {
        for (int i = 0; i < studentCount; i++) {
            Student student = getStudent(i);
            if (student.getId() == id) {
                return student;
            }
        }
        return null; // Không tìm thấy
    }

    public void close() throws IOException {
        raf.close();
    }
}
