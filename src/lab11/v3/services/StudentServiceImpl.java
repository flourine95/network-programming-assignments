package lab11.v3.services;

import lab11.v3.daos.DBConnection;
import lab11.v3.daos.StudentDAO;
import lab11.v3.models.Student;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class StudentServiceImpl extends UnicastRemoteObject implements IStudentService, Serializable {

    private final StudentDAO studentDao;

    public StudentServiceImpl() throws RemoteException, SQLException, ClassNotFoundException {
        super();
        this.studentDao = new StudentDAO(new DBConnection().getConnection());
    }

    @Override
    public Student findStudentById(int id) throws RemoteException {
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findStudentsByName(String name) throws RemoteException {
        return studentDao.findByName(name);
    }

    @Override
    public boolean add(Student student) throws RemoteException {
        return studentDao.add(student);
    }

    @Override
    public boolean update(Student student) throws RemoteException {
        return studentDao.update(student);
    }

    @Override
    public boolean delete(int id) throws RemoteException {
        return studentDao.delete(id);
    }

    @Override
    public List<Student> all() throws RemoteException {
        return studentDao.all();
    }
}
