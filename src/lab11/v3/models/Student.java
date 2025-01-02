package lab11.v3.models;

import java.io.Serializable;

public class Student implements Serializable {
    private int id;
    private String name;
    private String score;

    public Student() {
    }

    public Student(int id, String name, String score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public boolean hasId(int id) {
        return this.id == id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score='" + score + '\'' +
                '}';
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
