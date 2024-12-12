package lab9.v2.helpers;

import lab9.v2.models.Student;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static String format(List<Student> students) {
        return students.stream()
                .map(Student::toString)
                .collect(Collectors.joining("\n"));
    }

}
