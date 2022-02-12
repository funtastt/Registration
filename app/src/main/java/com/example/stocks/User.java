package com.example.stocks;

import java.util.Date;

public class User {
    private String login, password, name, profilePhotoLink, mail;
    private double balance = 0.0;
    private Date birthdayDate;

    public User() {
    }

    public User(String login, String password, String name, String profilePhotoLink, String mail, Date birthdayDate) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.profilePhotoLink = profilePhotoLink;
        this.mail = mail;
        this.birthdayDate = birthdayDate;
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

    public String getProfilePhotoLink() {
        return profilePhotoLink;
    }

    public void setProfilePhotoLink(String profilePhotoLink) {
        this.profilePhotoLink = profilePhotoLink;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }
}