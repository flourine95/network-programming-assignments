package exam.year23_24_code08.ex2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class Server {


    public void run() throws RemoteException, InterruptedException, SQLException, ClassNotFoundException {

        Registry registry = LocateRegistry.createRegistry(3564);
        IProductManager productManger = new IProductManagerImpl();
        registry.rebind("ProductManager", productManger);
        while (true) {
            System.out.println("Server is running...");
            Thread.sleep(3000);
        }
    }

    public static void main(String[] args) throws RemoteException, InterruptedException, SQLException, ClassNotFoundException {
        new Server().run();

    }
}
