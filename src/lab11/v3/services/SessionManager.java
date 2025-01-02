package lab11.v3.services;

import lab11.v3.models.Session;

import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager implements Remote {
    private static final Map<String, Session> sessions = new HashMap<>();
    private static final int SESSION_TIMEOUT_MINUTES = 30;

    public static String createSession(String username) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(SESSION_TIMEOUT_MINUTES);
        sessions.put(token, new Session(token, username, expiry));
        return token;

    }

    public static Session getSession(String token) {
        Session session = sessions.get(token);
        if (session != null && !session.isExpired()) {
            return session;
        }
        if (session != null && session.isExpired()) {
            sessions.remove(token);
        }
        return null;
    }

    public static void removeSession(String token) {
        sessions.remove(token);
    }
}
