package lab5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class StudentManager {
    private static final int NAME_LENGTH = 50;
    private RandomAccessFile raf;
    private int count;
    private int recordSize;
    private int nameLength;

    public StudentManager(String path) throws IOException {
        raf = new RandomAccessFile(path, "rw");
        if ((raf.length()) != 0) {
            count = raf.readInt();
            recordSize = raf.readInt();
            nameLength = (recordSize - 16) / 2;
        } else {
            count = 0;
            nameLength = NAME_LENGTH;
            recordSize = 16 + 2 * nameLength;
            raf.writeInt(count);
            raf.writeInt(recordSize);
        }
    }

    public void addStudent(Student student) throws IOException {
        raf.seek(raf.length());
        student.write(raf, nameLength);
        count++;
        raf.seek(0);
        raf.writeInt(count);
    }

    public Student getStudent(int index) throws IOException {
        if (index < 0 || index > count - 1) {
            System.err.println("Index out of bound");
        }

        long pos = ((long) index * recordSize) + 8;
        raf.seek(pos);
        Student st = new Student();
        st.load(raf, nameLength);
        return st;
    }

    public void updateStudent(int index, Student newStudent) throws IOException {
        if (index < 0 || index > count - 1) {
            System.err.println("Index out of bound");
        }
        long pos = 16 + (long) index * recordSize;
        raf.seek(pos);
        newStudent.write(raf, nameLength);
    }

    public Student findById(int id) throws IOException {
        for (int i = 0; i < count; i++) {
            long pos = ((long) i * recordSize) + 8;
            raf.seek(pos);
            Student student = new Student();
            student.load(raf, nameLength);
            if (student.hasId(id)) {
                return student;
            }
        }
        return null;
    }


    public static void main(String[] args) throws IOException {
        String path = "D:\\projects\\intelij\\network-programming-assignments\\src\\lab5\\students.dat";
        StudentManager studentManager = new StudentManager(path);
        studentManager.addStudent(new Student("Nguyen Van A", 1999, 8.5));
        studentManager.addStudent(new Student("Nguyen Van A", 1999, 8.5));
        studentManager.addStudent(new Student("Nguyen Van A", 1999, 8.5));
        System.out.println(studentManager.findById(13));

    }

}
