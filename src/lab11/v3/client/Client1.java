package lab11.v3.client;


import lab11.v3.services.IStudentService;
import lab11.v3.services.IUserService;

import java.io.BufferedReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client1 {
    private String sessionToken;
    private IUserService userService;
    private IStudentService studentService;

    public Client1() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(2000);
        userService = (IUserService) registry.lookup("USER_SERVICE");
        studentService = (IStudentService) registry.lookup("STUDENT_SERVICE");
    }

    public void login(String username, String password) throws RemoteException {
        boolean success = userService.login(username, password);
        if (success) {
            // Giả định server trả về token (cần sửa UserService để trả token)
            System.out.println("Login successful! Token: " + sessionToken);
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    public void fetchAllUsers() throws RemoteException {
        try {
            userService.all(sessionToken).forEach(System.out::println);
        } catch (RemoteException e) {
            System.out.println("Session expired or invalid. Please log in again.");
        }
    }

}
