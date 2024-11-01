package lab5.v2;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String file = "lab5/v2/students.dat";
        StudentManager studentManager = new StudentManager(file);
        studentManager.addStudent(new Student(1, "Nguyen Phi Long", 2004, 9));
        Student stu = studentManager.getStudent(1);
        System.out.println(stu.toString());
    }
}
