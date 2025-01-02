package lab11.v3.client;

import lab11.v3.services.IStudentService;
import lab11.v3.services.IUserService;

import java.io.BufferedReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client2 {
    BufferedReader console;
    IStudentService studentService;
    IUserService userService;
    String command;
    String prompt;

    public Client2() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(2000);
        studentService = (IStudentService) registry.lookup("STUDENT_SERVICE");
        userService = (IUserService) registry.lookup("USER_SERVICE");

    }

    public void run() throws RemoteException, InterruptedException {
        while (true) {

            System.out.println("Client2 is running...");
            Thread.sleep(2000);
            userService.all("aasd").forEach(System.out::println);
        }
    }

    public static void main(String[] args) throws NotBoundException, RemoteException, InterruptedException {
        new Client2().run();
    }

}
