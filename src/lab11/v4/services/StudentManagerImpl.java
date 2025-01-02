package lab11.v4.services;

import lab11.v4.models.Student;
import lab11.v4.repositories.StudentRepository;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentManagerImpl extends UnicastRemoteObject implements IStudentManager {
    private final SessionManagerImpl sessionManager;
    private final StudentRepository studentRepository;

    public StudentManagerImpl(SessionManagerImpl sessionManager, Connection dbConnection) throws RemoteException {
        this.sessionManager = sessionManager;
        studentRepository = new StudentRepository(dbConnection);
    }

    @Override
    public List<Student> all(String sessionId) throws RemoteException, SQLException {
        if (!sessionManager.isValid(sessionId)) {
            System.out.println("Session invalid!");
            throw new RemoteException("Session invalid!");
        }

        return studentRepository.all();
    }

    @Override
    public boolean create(String sessionId, Student student) throws RemoteException, SQLException {
        if (!sessionManager.isValid(sessionId)) {
            throw new RemoteException("Session invalid!");
        }
        return studentRepository.create(student);
    }

    @Override
    public boolean destroy(String sessionId, int id) throws RemoteException, SQLException {
        if (!sessionManager.isValid(sessionId)) {
            throw new RemoteException("Session invalid!");
        }
        return studentRepository.destroy(id);
    }

    @Override
    public boolean update(String sessionId, Student student) throws RemoteException, SQLException {
        if (!sessionManager.isValid(sessionId)) {
            throw new RemoteException("Session invalid!");
        }
        return studentRepository.update(student);
    }

    @Override
    public List<Student> filter(String sessionId, String name) throws RemoteException, SQLException {
        if (!sessionManager.isValid(sessionId)) {
            throw new RemoteException("Session invalid!");
        }
        return studentRepository.filter(name);
    }
}
