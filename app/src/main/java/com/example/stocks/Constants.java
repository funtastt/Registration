package com.example.stocks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.stocks.sqlite.UserCredentialsDatabaseHandler;
import com.example.stocks.ui.market.securities.Currency;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    public static String currentUserLogin = "";

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
        return FirebaseDatabase.getInstance("https://stocks-95f7e-default-rtdb.europe-west1.firebasedatabase.app/");
    }

    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] byteArray = baos.toByteArray();
        String bitmapString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();

        if (bitmapString.length() >= 2000000) {
            double ratio = bitmapString.length() / 2000000.0;
            int newWidth = (int) (originalWidth / ratio);
            int newHeight = (int) (originalHeight / ratio);

            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
            ByteArrayOutputStream newBaos = new ByteArrayOutputStream();
            newBitmap.compress(Bitmap.CompressFormat.PNG, 70, newBaos);
            byte[] newByteArray = newBaos.toByteArray();
            bitmapString = Base64.encodeToString(newByteArray, Base64.DEFAULT);
        }

        return bitmapString;
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

    public static void updateInfo(UserCredentialsDatabaseHandler mHandler) {
        User currentUser = mHandler.getUser();

        long lastLoginDate = currentUser.getLastLoginDate();
        long currentTime = new Date().getTime();
        long difference = currentTime - lastLoginDate;

        currentUser.setLastLoginDate(currentTime);
        currentUser = updateUserBalance(difference, currentUser);

        mHandler.updateUser(currentUser);
        DatabaseReference userRef = getFirebaseDatabase().getReference(currentUser.getLogin());
        userRef.setValue(currentUser);

    }

    private static User updateUserBalance(long difference, User currentUser) {
        double diffTimeInSeconds = difference / 1000.0;
        HashMap<String, Double> balances = currentUser.getBalances();
        HashMap<String, Double> incomes = currentUser.getIncomePerSecond();

        for (Currency currency : Currency.values()) {
            String currencyCode = currency.getCurrencyCode();
            double balance = balances.get(currencyCode);
            balance += incomes.get(currencyCode) * diffTimeInSeconds;

            balances.put(currencyCode, Math.round(balance * 10) / 10.0);
        }

        currentUser.setBalances(balances);

        return currentUser;
    }
}
