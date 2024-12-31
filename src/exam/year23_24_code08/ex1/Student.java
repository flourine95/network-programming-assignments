package exam.year23_24_code08.ex1;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Student {
    private int id;
    private String name;
    private double grade;
    private boolean isDeleted;

    public Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.isDeleted = false;
    }

    public Student() {
    }


    public Student( String name, double grade, boolean isDeleted) {
        this.name = name;
        this.grade = grade;
        this.isDeleted = isDeleted;
    }

    public Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public void write(RandomAccessFile raf, int nameLength) throws IOException {
        raf.writeInt(id);
        int c;
        for (int i = 0; i < nameLength; i++) {
            if (i < name.length()) c = name.charAt(i);
            else c = 0;
            raf.writeChar(c);
        }
        raf.writeDouble(grade);
        raf.writeBoolean(isDeleted);
    }

    public void read(RandomAccessFile raf, int nameLength) throws IOException {
        id = raf.readInt();
        char c;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < nameLength; i++) {
            c = raf.readChar();
            str.append(c);
        }
        name = str.toString().trim();
        grade = raf.readDouble();
        isDeleted = raf.readBoolean();
    }
}
