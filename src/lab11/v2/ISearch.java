package lab11.v2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ISearch extends Remote {
    boolean checkUsername(String username) throws RemoteException;

    int login(String username, String password) throws RemoteException;

    void logout(int sessionId) throws RemoteException;

    List<Student> findById(int sessionId, int id) throws RemoteException;

    List<Student> findByName(int sessionId, String partName) throws RemoteException;
}
