package lab11.v3.services;

import lab11.v3.models.Student;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IStudentService extends Remote {
    Student findStudentById(int id) throws RemoteException;

    List<Student> findStudentsByName(String name) throws RemoteException;

    boolean add(Student student) throws RemoteException;

    boolean update(Student student) throws RemoteException;

    boolean delete(int id) throws RemoteException;
    List<Student> all() throws RemoteException;
}
