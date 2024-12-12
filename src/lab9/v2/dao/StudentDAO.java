package lab9.v2.dao;


import lab9.v2.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private List<Student> students = new ArrayList<>();

    public StudentDAO() {
        students.add(new Student("SV001", "Nguyễn Thị Hoa Hồng", 8.5));
        students.add(new Student("SV002", "Trần Văn Minh", 7.2));
        students.add(new Student("SV003", "Lê Hồng Ánh", 9.0));
        students.add(new Student("SV004", "Lê Hồng Ánh", 9.0));
        students.add(new Student("SV005", "Lê Hồng Ánh", 9.0));
        students.add(new Student("SV006", "Lê Hồng Ánh", 9.0));
        students.add(new Student("SV007", "Lê Hồng Ánh", 9.0));
        students.add(new Student("SV008", "Lê Hồng Ánh", 9.0));
    }
    
    public List<Student> all() {
        return students;
    }

    public Student findById(String id) {
        return students
                .stream()
                .filter(s -> s.hasId(id))
                .findFirst()
                .orElse(null);
    }

    public List<Student> findByName(String name) {
        return students.stream().filter(s -> s.hasName(name)).toList();
    }
}
