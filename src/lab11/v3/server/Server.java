package lab11.v3.server;

import lab11.v3.services.SessionManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws RemoteException, SQLException, ClassNotFoundException {
        Registry registry = LocateRegistry.createRegistry(2000);
        registry.rebind("SESSION_MANAGER", new SessionManager());
        System.out.println("Server is running...");
    }
}