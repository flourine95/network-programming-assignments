package lab11.v1.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StudentService extends Remote {
    String login(String username, String password) throws RemoteException;

    String findById(int id) throws RemoteException;

    String findByName(String name) throws RemoteException;
}
