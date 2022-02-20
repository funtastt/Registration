package com.example.stocks;

public class CurrentUser {
    private static User currentUser;

    static public void setUser(User user, boolean isCurrentUserInfoChanged) {
        if (currentUser == null || isCurrentUserInfoChanged) {
            currentUser = user;
        }
    }

    static public User getUser() {
        return currentUser;
    }
}