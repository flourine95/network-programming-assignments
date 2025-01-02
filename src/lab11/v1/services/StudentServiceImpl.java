package lab11.v1.services;

import lab11.v1.daos.StudentDAO;
import lab11.v1.daos.UserDAO;
import lab11.v1.models.Student;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class StudentServiceImpl extends UnicastRemoteObject implements StudentService {

    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;
    private UserDAO userDAO;

    public StudentServiceImpl() throws RemoteException {
        super();
        studentDAO = new StudentDAO();
        userDAO = new UserDAO();
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        if (userDAO.validateUser(username, password)) {
            return "Login successful";
        }
        return "Invalid username or password";
    }

    @Override
    public String findById(int id) throws RemoteException {
        List<Student> students = studentDAO.findById(id);
        if (students.isEmpty()) {
            return "Không tìm thấy";
        }
        return students.get(0).toString();
    }

    @Override
    public String findByName(String namePart) throws RemoteException {
        List<Student> students = studentDAO.findByName(namePart);
        if (students.isEmpty()) {
            return "Không tìm thấy";
        }
        StringBuilder result = new StringBuilder();
        for (Student student : students) {
            result.append(student.toString()).append("\n");
        }
        return result.toString();
    }
}