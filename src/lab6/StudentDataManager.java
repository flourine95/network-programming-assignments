package lab6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StudentDataManager {
    private List<Student> students;

    public StudentDataManager() {
        this.students = new ArrayList<>();
        System.out.println();
    }

    public List<Student> loadData(String stFile, String gradeFile, String charset) throws IOException {
        List<Student> students = new ArrayList<>();
        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(new FileInputStream(stFile), charset));
        String line;
        while ((line = bf.readLine()) != null) {

        }
        return null;
    }
}
