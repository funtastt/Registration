package com.example.stocks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.stocks.sqlite.UserCredentialsDatabaseHandler;

import java.util.Date;

public class RemindPasswordActivity extends AppCompatActivity {

    UserCredentialsDatabaseHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_password);

        mHandler = new UserCredentialsDatabaseHandler(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        User currentUser = mHandler.getUser();
        currentUser.setLastLoginDate(new Date().getTime());
        mHandler.updateUser(currentUser);
    }
}