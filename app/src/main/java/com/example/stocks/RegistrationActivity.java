package com.example.stocks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    TextView mRegistrationTextView, mLogInTextView;
    EditText mLoginEditText, mPasswordEditText, mNameEditText, mMailEditText;
    Button mSignUp;

    String profileImageLink = "user.png";
    Date birthdayDate = new Date();

    private final Pattern MAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private final Pattern PASSWORD_REGEX = Pattern.compile("^[a-zA-Z0-9._\\-!@#$%^&*()=+\"№;:?']{3,}$");
    private final Pattern LOGIN_REGEX = Pattern.compile("^[a-zA-Z0-9._\\-]{2,}$");
    private final Pattern NAME_REGEX = Pattern.compile("^[a-zA-Z\\-]{2,}$");

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

        mSignUp.setOnClickListener(view -> signUp());

        mLogInTextView.setOnClickListener(view -> goToLoginPage());
    }

    private FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance("https://stocks-95f7e-default-rtdb.europe-west1.firebasedatabase.app//");
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

        User user = new User(login, password, name, profileImageLink, mail, birthdayDate);

        FirebaseDatabase database = getFirebaseDatabase();
        DatabaseReference userRef = database.getReference(login);

        userRef.get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.exists()) {
                Toast.makeText(RegistrationActivity.this, "User with this login is already registered", Toast.LENGTH_SHORT).show();
            } else {
                userRef.setValue(user);
                login(user);
            }
        });
    }

    private void goToLoginPage() {
        Intent goToLoginPageIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(goToLoginPageIntent);
    }

    private void login(User user) {
        CurrentUser.setUser(user);
        Intent loginIntent = new Intent(RegistrationActivity.this, MainPageActivity.class);
        startActivity(loginIntent);
    }

    private boolean validateMailString(String emailStr) {
        Matcher matcher = MAIL_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private boolean validateLoginString(String loginStr) {
        Matcher matcher = LOGIN_REGEX.matcher(loginStr);
        return matcher.find();
    }

    private boolean validatePasswordString(String passwordStr) {
        Matcher matcher = PASSWORD_REGEX.matcher(passwordStr);
        return matcher.find();
    }

    private boolean validateNameString(String nameStr) {
        Matcher matcher = NAME_REGEX.matcher(nameStr);
        return matcher.find();
    }
}