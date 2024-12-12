package lab9.v1;

public class Student {
    private String id;
    private String fullName;
    private double avgScore;

    public Student(String id, String fullName, double avgScore) {
        this.id = id;
        this.fullName = fullName;
        this.avgScore = avgScore;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public double getAvgScore() { return avgScore; }
    
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
