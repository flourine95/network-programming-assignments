package lab9.v2.models;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean hasUsername(String username) {
        return this.username.equals(username);
    }

    public boolean hasPassword(String password) {
        return this.password.equals(password);
    }

    public boolean login(String username, String password) {
        return hasUsername(username) && hasPassword(password);
    }
}
