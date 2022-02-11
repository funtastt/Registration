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

public class RegistrationActivity extends AppCompatActivity {
    TextView mRegistrationTextView, mLogInTextView;
    EditText mLoginEditText, mPasswordEditText, mNameEditText;
    Button mSignUp;

    String profileImageLink = "user.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mRegistrationTextView = findViewById(R.id.registration_text);
        mLogInTextView = findViewById(R.id.log_in_text);
        mLoginEditText = findViewById(R.id.login_registration);
        mPasswordEditText = findViewById(R.id.password_registration);
        mNameEditText = findViewById(R.id.name_registration);
        mSignUp = findViewById(R.id.sign_up_button);

        mSignUp.setOnClickListener(view -> signUp());

        mLogInTextView.setOnClickListener(view -> goToLoginPage());
    }

    private FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance("https://stocks-95f7e-default-rtdb.europe-west1.firebasedatabase.app//");
    }

    private void signUp() {
        String login = mLoginEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String name = mNameEditText.getText().toString();

        if (login.isEmpty()) {
            Toast.makeText(this, "Login is empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()){
            Toast.makeText(this, "Password is empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.isEmpty()) {
            Toast.makeText(this, "Name is empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(login, password, name, profileImageLink);

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
}