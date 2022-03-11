package com.example.stocks;

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
}
