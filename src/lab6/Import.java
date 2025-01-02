package lab6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Import {
    public static void main(String[] args) throws IOException {
        String student = "D:\\projects\\intelij\\network-programming-assignments\\src\\lab6\\grade.txt";
        String grade = "D:\\projects\\intelij\\network-programming-assignments\\src\\lab6\\name.txt";
        List<Student> students = importData(student, grade, "UTF-8");
        students.forEach(System.out::println);
    }

    public static List<Student> importData(String nameFile, String gradeFile, String charset) throws IOException {
        List<Student> students = new ArrayList<>();
        BufferedReader br;
        String line;
        br = new BufferedReader(new InputStreamReader(new FileInputStream(nameFile), charset));
        // bỏ byte notepad
        br.read();
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, " \t");
            System.out.println(st.countTokens());
            students.add(new Student(Integer.parseInt(st.nextToken()), st.nextToken()));
        }
        br.close();
        br = new BufferedReader(new InputStreamReader(new FileInputStream(gradeFile), charset));
        br.read();
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, " \t");
            int id = Integer.parseInt(st.nextToken());
            int count = 0;
            double total = 0;
            while (st.hasMoreTokens()) {
                count++;
                total += Double.parseDouble(st.nextToken());
            }
            double grade = total / count;

            for (Student student : students) {
                if (student.id == id) {
                    student.grade = grade;
                    break;
                }
            }
        }
        br.close();
        return students;
    }
}
