package lab9.v2.dao;


import lab9.v2.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    List<User> users = new ArrayList<>();

    public UserDAO() {
        users.add(new User("admin", "pasword"));
    }


    public boolean isValidLogin(String user, String pass) {
        return users.stream().anyMatch(u -> u.login(user, pass));
    }
}
