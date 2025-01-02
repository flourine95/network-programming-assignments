package lab11.v3.services;

import lab11.v3.daos.DBConnection;
import lab11.v3.daos.UserDAO;
import lab11.v3.models.Session;
import lab11.v3.models.User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl extends UnicastRemoteObject implements IUserService, Serializable {
    private final UserDAO userDAO;

    public UserServiceImpl() throws RemoteException, SQLException, ClassNotFoundException {
        super();
        this.userDAO = new UserDAO(new DBConnection().getConnection());
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        boolean isValid = userDAO.isValidUser(username, password);
        if (isValid) {
            String token = SessionManager.createSession(username);
            System.out.println("User " + username + " logged in with session: " + token);
            return true;
        }
        return false;
    }



    @Override
    public List<User> all(String token) throws RemoteException {
        Session session = SessionManager.getSession(token);
        if (session == null) {
            throw new RemoteException("Session invalid or expired.");
        }
        return userDAO.all();
    }
}
