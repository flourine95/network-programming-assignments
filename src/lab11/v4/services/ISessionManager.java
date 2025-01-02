package lab11.v4.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISessionManager extends Remote {
    String login(String username, String password) throws RemoteException;

    void logout(String sessionId) throws RemoteException;

    boolean isValid(String sessionId) throws RemoteException;


}
