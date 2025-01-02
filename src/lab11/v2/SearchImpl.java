package lab11.v2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchImpl extends UnicastRemoteObject implements ISearch {
    private Dao dao;
    static int SID =0;
    private Set<Integer> sessions;
    // xu li truong hop moi ket noi 1 DAO
//    private Map<Integer,Dao> sessions

    // khong dong ket noi

    public SearchImpl() throws RemoteException {
        super();
        dao = new Dao();
        sessions = new HashSet<Integer>();
    }

    @Override
    public boolean checkUsername(String username) throws RemoteException {
        // try catch
//        return dao.checkUsername(username);
        return false;
    }

    @Override
    public int login(String username, String password) throws RemoteException {
//        if (dao.checkUsername(username, password)) {
//            sessions.add(SID);
//            return SID++;
//        }
        return -1;
    }

    @Override
    public void logout(int sessionId) throws RemoteException {

    }

    @Override
    public List<Student> findById(int sessionId, int id) throws RemoteException {
        if (sessions.contains(sessionId)) {
//            dao.findById(id);
        }
        return null;
    }

    @Override
    public List<Student> findByName(int sessionId, String partName) throws RemoteException {
        return List.of();
    }
}
