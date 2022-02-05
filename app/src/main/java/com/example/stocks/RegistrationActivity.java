package com.example.stocks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    String profileImageLink = "https://firebasestorage.googleapis.com/v0/b/stocks-95f7e.appspot.com/o/images%2Fuser.png?alt=media&token=99b47971-03f1-4ea8-b36b-59768692e3c7";

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

        mLogInTextView.setOnClickListener(view -> logIn());
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
                Toast.makeText(RegistrationActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logIn() {
        Intent loginIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}