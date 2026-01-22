package models.Users;

import Enums.PlayerColor;

import java.util.*;

public class UserManager {

    // works like a database
    //manages login, register, who are online, users etc

    private Map<String, User> users = new HashMap<>(); //  username -> User


    // Registers a new user if username is unique and input is valid
    public boolean register(String username) {
        if (username == null || username.trim().isEmpty()) return false;

        username = username.trim();
        if (users.containsKey(username)) return false;

        users.put(username, new User(username));
        return true;
    }


    //returns the user by their username
    public User getUser(String username) {
        return users.get(username);
    }

    public Set<String> getUserNameSet() {
        return users.keySet();
    }

    //returns all users (who registered before) in the database
    public Collection<User> getUsers() {
        return users.values();
    }

    public void clearAllUsers() {
        users.clear();
    }


}
