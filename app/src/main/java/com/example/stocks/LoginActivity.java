package com.example.stocks;

import static com.example.stocks.Constants.currentUserLogin;
import static com.example.stocks.Constants.getFirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stocks.sqlite.UserCredentialsDatabaseHandler;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    TextView mLogInTextView, mForgotPasswordTextView, mBackToRegistrationPageTextView;
    EditText mLoginEditText, mPasswordEditText;
    Button mLoginButton;
    UserCredentialsDatabaseHandler mUserCredentialsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserCredentialsHandler = new UserCredentialsDatabaseHandler(this);
        if (mUserCredentialsHandler.checkIfUserLoggedIn()) {
            currentUserLogin = mUserCredentialsHandler.getUser().getLogin();
            successfulLogin();
        }

        mLogInTextView = findViewById(R.id.log_in_main_text);
        mLoginEditText = findViewById(R.id.login_login);
        mPasswordEditText = findViewById(R.id.password_login);
        mLoginButton = findViewById(R.id.log_in_button);
        mForgotPasswordTextView = findViewById(R.id.forgot_password_text);
        mBackToRegistrationPageTextView = findViewById(R.id.back_to_registration_text);
        
        mLoginButton.setOnClickListener(view -> login());
        
        mForgotPasswordTextView.setOnClickListener(view -> remindPassword());

        mBackToRegistrationPageTextView.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
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
                    mUserCredentialsHandler.addUser(user);
                    currentUserLogin = user.getLogin();
                    successfulLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "The password or the login is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Error", "e", e);
                Toast.makeText(LoginActivity.this, "ahhaha", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {

    }
}
