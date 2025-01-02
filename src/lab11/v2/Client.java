package lab11.v2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    BufferedReader console;
    ISearch dao;
    String command;
    String prompt;

    public Client() throws RemoteException, NotBoundException {
        this.console = new BufferedReader(new InputStreamReader(System.in));
        Registry registry = LocateRegistry.getRegistry(2000);
        this.dao = (ISearch) registry.lookup("SEARCH");

    }
    public void run() {
        //..
        // dao.logout

    }

    public static void main(String[] args) {

    }
}
