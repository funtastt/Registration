package com.example.stocks;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegistrationActivity extends AppCompatActivity {
    private static final int SIGN_IN_CODE = 1;
    TextView mRegistrationTextView, mCustomizeYourProfilePhotoTextView;
    EditText mLoginEditText, mPasswordEditText, mNameEditText;
    ImageView mProfilePhotoImageView;
    Button mSignUp;


    // for saving profile image
    Uri uri;
    private final int PICK_IMAGE_REQUEST = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mRegistrationTextView = findViewById(R.id.registration_text);
        mLoginEditText = findViewById(R.id.login_registration);
        mPasswordEditText = findViewById(R.id.password_registration);
        mNameEditText = findViewById(R.id.name_registration);
        mCustomizeYourProfilePhotoTextView = findViewById(R.id.customize_your_profile_photo_text);
        mProfilePhotoImageView = findViewById(R.id.profile_photo_registration);
        mSignUp = findViewById(R.id.sign_up_button);

        mSignUp.setOnClickListener(view -> signUp());

        mProfilePhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
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
        String profileImageLink = getProfileImageLink();

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

        userRef.setValue(user);
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
    }

    String uploadedImageUrl = "";
    private String getProfileImageLink() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        String link = uri == null ? "user.png" : uri.getLastPathSegment();

        StorageReference uploadImageRef = storageRef.child("images/"+ link);

        if (uri == null) uri = Uri.parse("android.resource://com.example.stocks/" + R.drawable.user);

        UploadTask uploadTask = uploadImageRef.putFile(uri);

        // Как сделать, чтобы асинхронный uploadedImageUrl сначала получал результат, а потом его возвращал?
        uploadTask.addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(task -> {
                uploadedImageUrl = task.getResult().toString();
        })).addOnFailureListener(e -> {});

        return uploadedImageUrl;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {

                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                uri);
                mProfilePhotoImageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "hahaahhaa", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
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