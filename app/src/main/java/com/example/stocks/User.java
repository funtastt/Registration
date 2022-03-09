package com.example.stocks;

import com.example.stocks.ui.market.securities.Currency;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;

public class User {

    private String login, password, name, mail, profilePhotoLink;
    private HashMap<String, Double> balances = new HashMap<String, Double>() {
        {
            put(Currency.RUB.getCurrencyCode(), 10000.0);
            put(Currency.USD.getCurrencyCode(), 0.0);
            put(Currency.EUR.getCurrencyCode(), 0.0);
            put(Currency.GBP.getCurrencyCode(), 0.0);
            put(Currency.JPY.getCurrencyCode(), 0.0);
            put(Currency.CHF.getCurrencyCode(), 0.0);
        }
    };
    private long birthdayDate, lastLoginDate;
    private int userId;

    public User() {
    }

    public User(String login, String password, String name, String mail, long birthdayDate, String profilePhotoLink) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.profilePhotoLink = profilePhotoLink;
        this.mail = mail;
        this.birthdayDate = birthdayDate;
        this.lastLoginDate = new Date().getTime();
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

    public HashMap<String, Double> getBalances() {
        return balances;
    }

    public void setBalances(HashMap<String, Double> balances) {
        this.balances = balances;
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

    public long getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(long birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(long lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
