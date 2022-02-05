package com.example.stocks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    TextView mLogInTextView, mForgotPasswordTextView;
    EditText mLoginEditText, mPasswordEditText;
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        mLogInTextView = findViewById(R.id.log_in_main_text);
        mLoginEditText = findViewById(R.id.login_login);
        mPasswordEditText = findViewById(R.id.password_login);
        mLoginButton = findViewById(R.id.log_in_button);
        mForgotPasswordTextView = findViewById(R.id.forgot_password_text);
        
        mLoginButton.setOnClickListener(view -> login());
        
        mForgotPasswordTextView.setOnClickListener(view -> remindPassword());
    }

    private FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance("https://stocks-95f7e-default-rtdb.europe-west1.firebasedatabase.app//");
    }
    
    private void login() {
        String login = mLoginEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        
        DatabaseReference userRef = getFirebaseDatabase().getReference().child(login);

        if (login.isEmpty()) {
            Toast.makeText(this, "Login is empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()){
            Toast.makeText(this, "Password is empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        userRef.get().addOnSuccessListener(dataSnapshot -> {
            if (!dataSnapshot.exists()) {
                Toast.makeText(LoginActivity.this, "User with this login doesn't exists...", Toast.LENGTH_SHORT).show();
            } else {
                User user = dataSnapshot.getValue(User.class);
                if (password.equals(user.getPassword())) {
                    successfulLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "The password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void remindPassword() {
        Intent remindPasswordIntent = new Intent(LoginActivity.this, RemindPasswordActivity.class);
        startActivity(remindPasswordIntent);
    }

    private void successfulLogin() {
        Intent mainPageIntent = new Intent(LoginActivity.this, MainPageActivity.class);
        startActivity(mainPageIntent);
    }
}
