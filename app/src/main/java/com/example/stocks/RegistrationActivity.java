package com.example.stocks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {
    private static final int SIGN_IN_CODE = 1;
    TextView mRegistrationTextView;
    EditText mLoginEditText, mPasswordEditText;
    Button mSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mRegistrationTextView = findViewById(R.id.registration_text);
        mLoginEditText = findViewById(R.id.login_registration);
        mPasswordEditText = findViewById(R.id.password_registration);
        mSignUp = findViewById(R.id.sign_up_button);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegistrationActivity.this, "hello", Toast.LENGTH_SHORT).show();
                connectToDatabase();
            }
        });
    }

    private void signIn() {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void connectToDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://stocks-95f7e-default-rtdb.europe-west1.firebasedatabase.app//");
        String login = mLoginEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        User user = new User(login, password);

        DatabaseReference userRef = database.getReference(login);
        userRef.setValue(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
        }
    }

    private class User {
        private String login, password, name = "Anonymous", avatar = "lalalalala";
        private double balance = 0.0;

        public User(String login, String password) {
            this.login = login;
            this.password = password;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}