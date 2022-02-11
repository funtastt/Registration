package com.example.stocks;

public class CurrentUser {
    private static User currentUser;

    static public void setUser(User user) {
        if (currentUser == null) {
            currentUser = user;
        }
    }

    static public User getUser() {
        return currentUser;
    }
}
