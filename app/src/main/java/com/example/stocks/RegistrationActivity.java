package com.example.stocks;

import static com.example.stocks.Constants.*;
import static com.example.stocks.StaticMethods.convertBitmapToString;
import static com.example.stocks.StaticMethods.getFirebaseDatabase;
import static com.example.stocks.StaticMethods.validateLoginString;
import static com.example.stocks.StaticMethods.validateMailString;
import static com.example.stocks.StaticMethods.validateNameString;
import static com.example.stocks.StaticMethods.validatePasswordString;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stocks.sqlite.UserCredentialsDatabaseHandler;
import com.example.stocks.ui.market.securities.Currency;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    TextView mRegistrationTextView, mLogInTextView;
    EditText mLoginEditText, mPasswordEditText, mNameEditText, mMailEditText;
    Button mSignUp;

    String defaultProfileImageBitmapString;
    long birthdayDate = new Date().getTime();
    UserCredentialsDatabaseHandler mUserCredentialsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mRegistrationTextView = findViewById(R.id.registration_text);
        mLogInTextView = findViewById(R.id.log_in_text);
        mLoginEditText = findViewById(R.id.login_registration);
        mPasswordEditText = findViewById(R.id.password_registration);
        mNameEditText = findViewById(R.id.name_registration);
        mMailEditText = findViewById(R.id.mail_registration);
        mSignUp = findViewById(R.id.sign_up_button);

        // TODO: Implement disabling repeatedly pressing the button logic
        mSignUp.setOnClickListener(view -> signUp());
        mLogInTextView.setOnClickListener(view -> goToLoginPage());

        mUserCredentialsHandler = new UserCredentialsDatabaseHandler(this);
        getDefaultProfilePhotoBitmap();
    }

    private void signUp() {
        String login = mLoginEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String name = mNameEditText.getText().toString().trim();
        String mail = mMailEditText.getText().toString().trim();

        if (login.isEmpty()) {
            Toast.makeText(this, "Login is empty.", Toast.LENGTH_SHORT).show();
            return;
        } if (password.isEmpty()){
            Toast.makeText(this, "Password is empty.", Toast.LENGTH_SHORT).show();
            return;
        } if (name.isEmpty()) {
            Toast.makeText(this, "Name is empty.", Toast.LENGTH_SHORT).show();
            return;
        } if (mail.isEmpty()) {
            Toast.makeText(this, "Mail is empty.", Toast.LENGTH_SHORT).show();
            return;
        } if (!validateMailString(mail)) {
            Toast.makeText(this, "Entered mail is incorrect.", Toast.LENGTH_SHORT).show();
            return;
        } if (!validateLoginString(login)) {
            Toast.makeText(this, "Entered login is incorrect.", Toast.LENGTH_SHORT).show();
            return;
        } if (!validatePasswordString(password)) {
            Toast.makeText(this, "Entered password is incorrect.", Toast.LENGTH_SHORT).show();
            return;
        } if (!validateNameString(name)) {
            Toast.makeText(this, "Entered name is incorrect.", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(login, password, name, mail, birthdayDate, defaultProfileImageBitmapString);

        DatabaseReference userRef = getFirebaseDatabase().getReference(login);

        userRef.get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.exists()) {
                Toast.makeText(RegistrationActivity.this, "User with this login is already registered", Toast.LENGTH_SHORT).show();
            } else {
                userRef.setValue(user);
                mUserCredentialsHandler.addUser(user);
                currentUserLogin = user.getLogin();
                login();
            }
        });
    }

    private void goToLoginPage() {
        Intent goToLoginPageIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(goToLoginPageIntent);
    }

    private void login() {
        Intent loginIntent = new Intent(RegistrationActivity.this, MainPageActivity.class);
        startActivity(loginIntent);
    }

    private void getDefaultProfilePhotoBitmap() {
        Bitmap defaultProfileImageBitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.user);
        defaultProfileImageBitmapString = convertBitmapToString(defaultProfileImageBitmap);
    }

    @Override
    public void onBackPressed() {

    }
}