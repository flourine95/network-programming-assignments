package exam.year23_24_code08.ex1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private static final int NAME_LENGTH = 50;
    private static final int INT_SIZE = 4;
    private static final int DOUBLE_SIZE = 8;
    private static final int BOOLEAN_SIZE = 1;

    private final RandomAccessFile raf;
    private int count;
    private final int recordSize;
    private final int nameLength;

    public StudentManager(String path) throws IOException {
        raf = new RandomAccessFile(path, "rw");
        if (raf.length() == 0) {
            count = 0;
            nameLength = NAME_LENGTH;
            recordSize = INT_SIZE + DOUBLE_SIZE + BOOLEAN_SIZE + 2 * nameLength;
            raf.writeInt(count);
            raf.writeInt(recordSize);
        } else {
            count = raf.readInt();
            recordSize = raf.readInt();
            nameLength = (recordSize - 13) / 2;
        }
    }

    public void add(Student student) throws IOException {
        raf.seek(raf.length());
        student.write(raf, nameLength);
        updateCount(++count);
    }

    public boolean delete(int studentId) throws IOException {
        long position = findStudentPositionById(studentId);
        if (position != -1) {
            raf.seek(position + INT_SIZE + DOUBLE_SIZE + nameLength * 2L);
            raf.writeBoolean(true);
            return true;
        }
        return false;
    }

    public Student get(int studentId) throws IOException {
        long position = findStudentPositionById(studentId);
        if (position != -1) {
            raf.seek(position);
            Student student = new Student();
            student.read(raf, nameLength);
            return student;
        }
        return null;
    }

    public List<Student> list() throws IOException {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            long position = getRecordPosition(i);
            raf.seek(position + INT_SIZE + DOUBLE_SIZE + nameLength * 2L);
            if (!raf.readBoolean()) {
                raf.seek(position);
                Student student = new Student();
                student.read(raf, nameLength);
                students.add(student);
            }
        }
        return students;
    }

    public boolean update(int studentId, Student newStudent) throws IOException {
        long position = findStudentPositionById(studentId);
        if (position != -1) {
            raf.seek(position + INT_SIZE + DOUBLE_SIZE + nameLength * 2L);
            if (!raf.readBoolean()) {
                raf.seek(position);
                newStudent.setId(studentId);
                newStudent.write(raf, nameLength);
                return true;
            }
        }
        return false;
    }

    private long findStudentPositionById(int studentId) throws IOException {
        for (int i = 0; i < count; i++) {
            long position = getRecordPosition(i);
            raf.seek(position);
            if (raf.readInt() == studentId) {
                return position;
            }
        }
        return -1;
    }

    private long getRecordPosition(int index) {
        return 8 + (long) index * recordSize;
    }

    private void updateCount(int newCount) throws IOException {
        raf.seek(0);
        raf.writeInt(newCount);
    }

    public static void main(String[] args) throws IOException {
        StudentManager manager = new StudentManager("src/exam/year23_24_code08/ex1/students.dat");
        manager.add(new Student(1, "Phi Long 2", 1.0));
        manager.add(new Student(2, "Phi Long 3", 2.0));
        manager.add(new Student(3, "Phi Long 3", 2.0));
        manager.add(new Student(4, "Phi Long 3", 2.0));
        manager.update(1, new Student("Phi Long da update", 1.0));
        manager.list().forEach(System.out::println);

    }
}
