package com.example.stocks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {
    private static final int SIGN_IN_CODE = 1;
    TextView mRegistrationTextView;
    EditText mLoginEditText, mPasswordEditText, mNameEditText;
    Button mSignUp;

    String profileImageLink = "https://firebasestorage.googleapis.com/v0/b/stocks-95f7e.appspot.com/o/images%2Fuser.png?alt=media&token=99b47971-03f1-4ea8-b36b-59768692e3c7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mRegistrationTextView = findViewById(R.id.registration_text);
        mLoginEditText = findViewById(R.id.login_registration);
        mPasswordEditText = findViewById(R.id.password_registration);
        mNameEditText = findViewById(R.id.name_registration);
        mSignUp = findViewById(R.id.sign_up_button);

        mSignUp.setOnClickListener(view -> signUp());

    }

    @Override
    protected void onStart() {
        super.onStart();
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

        userRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(RegistrationActivity.this, "User with this login is already registered", Toast.LENGTH_SHORT).show();
                } else {
                    userRef.setValue(user);
                    Toast.makeText(RegistrationActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private class User {
        private String login, password, name, profilePhoto;
        private double balance = 0.0;

        public User(String login, String password, String name, String profilePhoto) {
            this.login = login;
            this.password = password;
            this.name = name;
            this.profilePhoto = profilePhoto;
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

        public String getProfilePhoto() {
            return profilePhoto;
        }

        public void setProfilePhoto(String profilePhoto) {
            this.profilePhoto = profilePhoto;
        }
    }
}