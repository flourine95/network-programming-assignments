package lab11.v4.services;

import lab11.v4.repositories.UserRepository;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManagerImpl extends UnicastRemoteObject implements ISessionManager {

    private final Map<String, String> sessions;
    private final UserRepository userRepository;

    public SessionManagerImpl(Connection dbConnection) throws RemoteException {
        sessions = new HashMap<>();
        userRepository = new UserRepository(dbConnection);
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        if (userRepository.validateLogin(username, password)) {
            String sessionId = UUID.randomUUID().toString();
            if (sessionId != null) {
                sessions.put(sessionId, username);
                return sessionId;
            } else {
                throw new RemoteException("Failed to generate session ID.");
            }
        }
        return null;
    }

    @Override
    public void logout(String sessionId) throws RemoteException {
        sessions.remove(sessionId);
    }

    @Override
    public boolean isValid(String sessionId) throws RemoteException {
        return sessions.containsKey(sessionId);
    }

    public Map<String, String> getSessions() {
        return sessions;
    }
}
