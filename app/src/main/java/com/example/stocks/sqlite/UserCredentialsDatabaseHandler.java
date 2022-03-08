package com.example.stocks.sqlite;

import static com.example.stocks.Constants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.stocks.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserCredentialsDatabaseHandler extends SQLiteOpenHelper {
    private static final String TABLE_USER_CREDENTIALS = "user_credentials";
    private static final String PRIMARY_KEY_ID = "id";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_BIRTHDAY_DATE = "birthday_date";
    private static final String KEY_PROFILE_PHOTO = "profile_photo";
    private static final String KEY_BALANCES = "balances";

    public UserCredentialsDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER_CREDENTIALS + "(" +
                PRIMARY_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_LOGIN + " TEXT, " +
                KEY_PASSWORD + " TEXT, " +
                KEY_NAME + " TEXT, " +
                KEY_MAIL + " TEXT, " +
                KEY_BIRTHDAY_DATE + " LONG, " +
                KEY_BALANCES + " TEXT, " +
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
        values.put(KEY_LOGIN, user.getLogin());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_MAIL, user.getMail());
        values.put(KEY_BIRTHDAY_DATE, user.getBirthdayDate());
        values.put(KEY_BALANCES, mapToString(user.getBalances()));
        values.put(KEY_PROFILE_PHOTO, user.getProfilePhotoLink());
        db.insert(TABLE_USER_CREDENTIALS, null, values);
        db.close();
    }

    private String mapToString(HashMap<String, Double> balances) {
        return new JSONObject(balances).toString();
    }

    public User getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER_CREDENTIALS, new String[] {KEY_LOGIN, KEY_PASSWORD, KEY_NAME, KEY_MAIL, KEY_BIRTHDAY_DATE, KEY_PROFILE_PHOTO, PRIMARY_KEY_ID, KEY_BALANCES }, null,
                null, null, null, null, String.valueOf(1));
        if (cursor != null)
            cursor.moveToFirst();
        User user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Long.parseLong(cursor.getString(4)), cursor.getString(5));
        user.setUserId(Integer.parseInt(cursor.getString(6)));
        String balancesString = cursor.getString(7);
        try {
            JSONObject object = new JSONObject(balancesString);
            Iterator<String> nameItr = object.keys();
            Map<String, Double> outMap = new HashMap<>();
            while(nameItr.hasNext()) {
                String name = nameItr.next();
                outMap.put(name, object.getDouble(name));
            }

            for (Map.Entry<String, Double> map : outMap.entrySet()) {
                Log.d("Error", map.getKey() + ", " + map.getValue());
            }

            } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public int updateUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LOGIN, user.getLogin());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_MAIL, user.getMail());
        values.put(KEY_BIRTHDAY_DATE, user.getBirthdayDate());
        values.put(KEY_BALANCES, mapToString(user.getBalances()));
        values.put(KEY_PROFILE_PHOTO, user.getProfilePhotoLink());
        return database.update(TABLE_USER_CREDENTIALS, values, PRIMARY_KEY_ID + " = ?", new String[]{String.valueOf(user.getUserId())});
    }

    public boolean checkIfUserLoggedIn() {
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "Select * from " + TABLE_USER_CREDENTIALS;
        Cursor cursor = database.rawQuery(q, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}

