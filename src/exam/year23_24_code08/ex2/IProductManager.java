package exam.year23_24_code08.ex2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IProductManager extends Remote {
    String sendWelcomeMessage() throws RemoteException;

    boolean add(Product product) throws RemoteException;

    Map<Integer, String> buy(List<Integer> products) throws RemoteException;

    boolean update(int id, Product newProduct) throws RemoteException;

    List<Product> find(String name) throws RemoteException;

    List<Product> all() throws RemoteException;

    boolean delete(int id) throws RemoteException;

    Product get(int id) throws RemoteException;

}
