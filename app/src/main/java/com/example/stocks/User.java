package com.example.stocks;

import com.example.stocks.ui.market.securities.Bond;
import com.example.stocks.ui.market.securities.Currency;
import com.example.stocks.ui.market.securities.Security;

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

    private HashMap<String, Double> incomePerSecond = new HashMap<String, Double>() {
        {
            put(Currency.RUB.getCurrencyCode(), 100.0);
            put(Currency.USD.getCurrencyCode(), 0.0);
            put(Currency.EUR.getCurrencyCode(), 0.0);
            put(Currency.GBP.getCurrencyCode(), 0.0);
            put(Currency.JPY.getCurrencyCode(), 0.0);
            put(Currency.CHF.getCurrencyCode(), 0.0);
        }
    };

    private HashMap<String, HashMap<String, Security>> properties = new HashMap<String, HashMap<String, Security>>(){
        {
            Bond governmentBond = new Bond("Government Bond", Currency.RUB, 1, 1000.0, 60.0, 52.0, 0.9999, 123);
            Bond largeCompanyBond = new Bond("Large Company Bond", Currency.RUB, 2, 5000.0, 450.0, 12.0, 0.9995, 234);
            Bond municipalBond = new Bond("Municipal Bond", Currency.RUB, 3, 20000.0, 2400.0, 24.0, 0.999, 345);
            Bond smallCompanyBond = new Bond("Small Company Bond", Currency.RUB, 4, 60000.0, 9000.0, 12.0, 0.997, 456);
            HashMap<String, Security> bonds = new HashMap<>();
            bonds.put(governmentBond.getName(), governmentBond);
            bonds.put(largeCompanyBond.getName(), largeCompanyBond);
            bonds.put(municipalBond.getName(), municipalBond);
            bonds.put(smallCompanyBond.getName(), smallCompanyBond);
            put(Currency.RUB.getCurrencyCode(), bonds);
            put(Currency.USD.getCurrencyCode(), bonds);
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

    public HashMap<String, Double> getIncomePerSecond() {
        return incomePerSecond;
    }

    public void setIncomePerSecond(HashMap<String, Double> incomePerSecond) {
        this.incomePerSecond = incomePerSecond;
    }

    public HashMap<String, HashMap<String, Security>> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, HashMap<String, Security>> properties) {
        this.properties = properties;
    }
}
