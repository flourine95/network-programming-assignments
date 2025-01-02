package lab11.v3.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Session implements Serializable {
    private String token;
    private String username;
    private LocalDateTime expiry;

    public Session(String token, String username, LocalDateTime expiry) {
        this.token = token;
        this.username = username;
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiry);
    }
}