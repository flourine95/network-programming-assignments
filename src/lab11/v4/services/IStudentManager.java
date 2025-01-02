package lab11.v4.services;

import lab11.v4.models.Student;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface IStudentManager extends Remote {
    List<Student> all(String sessionId) throws RemoteException, SQLException;

    boolean create(String sessionId, Student student) throws RemoteException, SQLException;

    boolean destroy(String sessionId, int id) throws RemoteException, SQLException;

    boolean update(String sessionId, Student student) throws RemoteException, SQLException;

    List<Student> filter(String sessionId, String name) throws RemoteException, SQLException;
}
