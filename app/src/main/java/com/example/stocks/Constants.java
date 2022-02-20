package com.example.stocks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.stocks.sqlite.UserCredentialsDatabaseHandler;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constants {
    public static final Pattern MAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern PASSWORD_REGEX = Pattern.compile("^[a-zA-Z0-9._\\-!@#$%^&*()=+\"№;:?']{3,}$");
    public static final Pattern LOGIN_REGEX = Pattern.compile("^[a-zA-Z0-9._\\-]{2,}$");
    public static final Pattern NAME_REGEX = Pattern.compile("^[a-zA-Z\\-]{2,}$");

    public static final int BIRTHDAY_PICKER_REQUEST_CODE = 1;
    public static final String CHOOSE_BIRTHDAY_DATE = "chooseBirthdayDate";
    public static final String INITIAL_BIRTHDAY_DATE = "initialBirthdayDate";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "user_data.db";

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

    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap convertStringToBitMap(String string) {
        try {
            byte[] encodeByte = Base64.decode(string, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
