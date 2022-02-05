package com.example.stocks;

class User {
    private String login, password, name, profilePhoto;
    private double balance = 0.0;

    public User() {
    }

    public User(String login, String password, String name, String profilePhoto) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.profilePhoto = profilePhoto;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}