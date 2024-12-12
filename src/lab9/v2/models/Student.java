package lab9.v2.models;

public class Student {
    private String id;
    private String fullName;
    private double avgScore;

    public Student(String id, String fullName, double avgScore) {
        this.id = id;
        this.fullName = fullName;
        this.avgScore = avgScore;
    }


    public boolean hasId(String id) {
        return this.id.equalsIgnoreCase(id);
    }

    public boolean hasName(String name) {
        return fullName.toLowerCase().contains(name.toLowerCase());
    }

    @Override
    public String toString() {
        return id + " - " + fullName + " - " + avgScore;
    }
}
