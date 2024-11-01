package lab5.v1;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        StudentManager manager = new StudentManager();

        manager.addStudent(new Student(1, "Alice", 2000, 3.5));
        manager.addStudent(new Student(2, "Bob", 1999, 3.7));
        manager.addStudent(new Student(3, "Charlie", 2001, 2.8));

        Student student = manager.getStudent(1);
        System.out.println("Retrieved Student: " + student.getName());
//
//        manager.updateStudent(1, new Student(2, "Bob Updated", 1999, 3.9));
//
//        Student foundStudent = manager.findById(2);
//        if (foundStudent != null) {
//            System.out.println("Found Student: " + foundStudent.getName());
//        }

//        manager.close();

    }
}
