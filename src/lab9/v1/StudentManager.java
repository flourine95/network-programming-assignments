package lab9.v1;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students = new ArrayList<>();

    public StudentManager() {
        students.add(new Student("SV001", "Nguyễn Thị Hoa Hồng", 8.5));
        students.add(new Student("SV002", "Trần Văn Minh", 7.2));
        students.add(new Student("SV003", "Lê Hồng Ánh", 9.0));
    }

    public List<Student> findById(String id) {
        return students.stream().filter(s -> s.hasId(id)).toList();
    }

    public List<Student> findByName(String name) {
        return students.stream().filter(s -> s.hasName(name)).toList();
    }
}
