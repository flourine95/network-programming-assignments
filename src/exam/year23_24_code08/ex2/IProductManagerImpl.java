package exam.year23_24_code08.ex2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class IProductManagerImpl extends UnicastRemoteObject implements IProductManager {
    private final Dao dao;

    protected IProductManagerImpl() throws RemoteException, SQLException, ClassNotFoundException {
        dao = new Dao();
    }

    @Override
    public String sendWelcomeMessage() throws RemoteException {
        return "Welcome to the product manager!";
    }

    @Override
    public boolean add(Product product) throws RemoteException {
        return dao.add(product);
    }

    @Override
    public Map<Integer, String> buy(List<Integer> products) throws RemoteException {
        return dao.buy(products);
    }

    @Override
    public boolean update(int id, Product newProduct) throws RemoteException {
        return dao.update(id, newProduct);
    }

    @Override
    public List<Product> find(String name) throws RemoteException {
        return dao.find(name);
    }

    @Override
    public List<Product> all() throws RemoteException {
        return dao.all();
    }

    @Override
    public boolean delete(int id) throws RemoteException {
        return dao.delete(id);
    }

    @Override
    public Product get(int id) throws RemoteException {
        return dao.get(id);
    }


}
