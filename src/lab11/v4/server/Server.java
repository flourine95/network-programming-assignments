package lab11.v4.server;

import lab11.v4.utils.DBConnection;
import lab11.v4.services.SessionManagerImpl;
import lab11.v4.services.StudentManagerImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.SQLException;

public class Server {

    public static void main(String[] args) throws RemoteException, SQLException, ClassNotFoundException {
        Connection connection = new DBConnection().getConnection();
        SessionManagerImpl sessionManager = new SessionManagerImpl(connection);
        StudentManagerImpl studentManager = new StudentManagerImpl(sessionManager, connection);

        Registry registry = LocateRegistry.createRegistry(2000);
        registry.rebind("SessionManager", sessionManager);
        registry.rebind("StudentManager", studentManager);

        while (true) {
            System.out.println("Server is running...");
            try {
                System.out.println("Session count: " + sessionManager.getSessions().size());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
