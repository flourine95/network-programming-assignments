package lab5.v1;

import java.nio.ByteBuffer;

public class Student {
    private int id;
    private String name;
    private int bYear;
    private double grade;

    public Student(int id, String name, int bYear, double grade) {
        this.id = id;
        this.name = name;
        this.bYear = bYear;
        this.grade = grade;
    }

    // Các phương thức getter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getbYear() {
        return bYear;
    }

    public double getGrade() {
        return grade;
    }

    public byte[] toByteArray() {
        byte[] nameBytes = name.getBytes();
        int nameLength = nameBytes.length;

        ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + nameLength + 4 + 8);
        buffer.putInt(id);
        buffer.putInt(nameLength);
        buffer.put(nameBytes);
        buffer.putInt(bYear);
        buffer.putDouble(grade);

        return buffer.array();
    }

    public static Student fromByteArray(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int id = buffer.getInt();
        int nameLength = buffer.getInt();
        byte[] nameBytes = new byte[nameLength];
        buffer.get(nameBytes);
        String name = new String(nameBytes);
        int bYear = buffer.getInt();
        double grade = buffer.getDouble();

        return new Student(id, name, bYear, grade);
    }

}
