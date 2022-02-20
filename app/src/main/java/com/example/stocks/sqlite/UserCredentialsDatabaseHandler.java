package com.example.stocks.sqlite;

import static com.example.stocks.Constants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.stocks.User;

public class UserCredentialsDatabaseHandler extends SQLiteOpenHelper {
    private static final String TABLE_USER_CREDENTIALS = "user_credentials";
    private static final String PRIMARY_KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_BIRTHDAY_DATE = "birthday_date";
    private static final String KEY_PROFILE_PHOTO = "profile_photo";

    public UserCredentialsDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER_CREDENTIALS + "(" +
                PRIMARY_KEY_LOGIN + " TEXT PRIMARY KEY, " +
                KEY_PASSWORD + " TEXT, " +
                KEY_NAME + " TEXT, " +
                KEY_MAIL + " TEXT, " +
                KEY_BIRTHDAY_DATE + " LONG, " +
                KEY_PROFILE_PHOTO + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_CREDENTIALS);
        onCreate(sqLiteDatabase);
    }

    public void cleanTable() {
        SQLiteDatabase sdb = this.getWritableDatabase();
        sdb.execSQL("delete from " + TABLE_USER_CREDENTIALS);
    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRIMARY_KEY_LOGIN, user.getLogin());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_MAIL, user.getMail());
        values.put(KEY_BIRTHDAY_DATE, user.getBirthdayDate());
        values.put(KEY_PROFILE_PHOTO, user.getProfilePhotoLink());
        db.insert(TABLE_USER_CREDENTIALS, null, values);
        db.close();
    }

    public User getUser(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER_CREDENTIALS, new String[] { PRIMARY_KEY_LOGIN, KEY_PASSWORD, KEY_NAME, KEY_MAIL, KEY_BIRTHDAY_DATE, KEY_PROFILE_PHOTO }, PRIMARY_KEY_LOGIN + "=?",
                new String[] { String.valueOf(login) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Long.parseLong(cursor.getString(4)), cursor.getString(5));
    }

    public int updateUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRIMARY_KEY_LOGIN, user.getLogin());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_MAIL, user.getMail());
        values.put(KEY_BIRTHDAY_DATE, user.getBirthdayDate());
        values.put(KEY_PROFILE_PHOTO, user.getProfilePhotoLink());
        return database.update(TABLE_USER_CREDENTIALS, values, PRIMARY_KEY_LOGIN + " = ?", new String[]{String.valueOf(user.getLogin())});
    }
}
