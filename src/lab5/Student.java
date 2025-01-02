package lab5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Student {
    private static int nextId = 1;
    private int id;
    private String name;
    private int birthYear;
    private double score;

    public Student(String name, int birthYear, double score) {
        this.id = nextId++;
        this.name = name;
        this.birthYear = birthYear;
        this.score = score;
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

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Student() {
    }

    public boolean hasId(int id) {
        return this.id == id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", score=" + score +
                '}';
    }

    public void write(RandomAccessFile raf, int nLen) throws IOException {
        raf.writeInt(id);
        int c;
        for (int i = 0; i < nLen; i++) {
            if (i < name.length()) {
                c = name.charAt(i);
            } else {
                c = 0;
            }
            raf.writeChar(c);
        }
        raf.writeInt(birthYear);
        raf.writeDouble(score);
    }

    public void load(RandomAccessFile raf, int nLen) throws IOException {
        id = raf.readInt();
        char c;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < nLen; i++) {
            c = raf.readChar();
            str.append(c);
        }
        name = str.toString().trim();
        birthYear = raf.readInt();
        score = raf.readDouble();
    }


}
