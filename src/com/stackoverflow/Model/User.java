package com.stackoverflow.Model;

import java.util.UUID;

public class User {
    private final String id;
    private final String username;
    private final String email;

    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public static User createUser(String username, String email) {
        UUID uuid = UUID.randomUUID();
        return new User(uuid.toString(), username, email);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
