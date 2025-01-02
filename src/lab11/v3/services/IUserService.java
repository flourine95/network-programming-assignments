package lab11.v3.services;


import lab11.v3.models.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IUserService extends Remote {
    boolean login(String username, String password) throws RemoteException;

    List<User> all(String token) throws RemoteException;
}
