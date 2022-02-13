package com.example.stocks;

import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constants {
    public static final Pattern MAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern PASSWORD_REGEX = Pattern.compile("^[a-zA-Z0-9._\\-!@#$%^&*()=+\"№;:?']{3,}$");
    public static final Pattern LOGIN_REGEX = Pattern.compile("^[a-zA-Z0-9._\\-]{2,}$");
    public static final Pattern NAME_REGEX = Pattern.compile("^[a-zA-Z\\-]{2,}$");

    public static boolean validateMailString(String emailStr) {
        Matcher matcher = Constants.MAIL_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validateLoginString(String loginStr) {
        Matcher matcher = Constants.LOGIN_REGEX.matcher(loginStr);
        return matcher.find();
    }

    public static boolean validatePasswordString(String passwordStr) {
        Matcher matcher = Constants.PASSWORD_REGEX.matcher(passwordStr);
        return matcher.find();
    }

    public static boolean validateNameString(String nameStr) {
        Matcher matcher = Constants.NAME_REGEX.matcher(nameStr);
        return matcher.find();
    }

    public static FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance("https://stocks-95f7e-default-rtdb.europe-west1.firebasedatabase.app//");
    }
}
