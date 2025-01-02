package lab11.v1.server;

import lab11.v1.services.StudentServiceImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            StudentServiceImpl obj = new StudentServiceImpl();

            Registry registry = LocateRegistry.createRegistry(2000);
            registry.rebind("StudentService", obj);

        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
