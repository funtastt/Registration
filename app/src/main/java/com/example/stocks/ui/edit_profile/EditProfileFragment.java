package com.example.stocks.ui.edit_profile;

import static android.app.Activity.RESULT_OK;
import static com.example.stocks.Constants.BIRTHDAY_PICKER_REQUEST_CODE;
import static com.example.stocks.Constants.CHOOSE_BIRTHDAY_DATE;
import static com.example.stocks.Constants.INITIAL_BIRTHDAY_DATE;
import static com.example.stocks.Constants.convertBitmapToString;
import static com.example.stocks.Constants.convertStringToBitMap;
import static com.example.stocks.Constants.getFirebaseDatabase;
import static com.example.stocks.Constants.validateLoginString;
import static com.example.stocks.Constants.validateMailString;
import static com.example.stocks.Constants.validateNameString;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.stocks.ChangePasswordActivity;
import com.example.stocks.CurrentUser;
import com.example.stocks.R;
import com.example.stocks.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class EditProfileFragment extends Fragment {

    private View editProfileFragment;
    private EditText mEditName, mEditLogin, mEditMail, mEditBirthday;
    private TextView mChangePassword;
    private Button mSubmitNameButton, mSubmitLoginButton, mSubmitProfilePhotoButton, mSubmitMailButton, mSubmitBirthdayButton;
    private ImageView mEditProfilePhoto;

    User mUser = CurrentUser.getUser();
    DatabaseReference userRef;

    private long birthdayDate = mUser.getBirthdayDate();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        editProfileFragment = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mEditName = editProfileFragment.findViewById(R.id.edit_profile_name_editView);
        mEditLogin = editProfileFragment.findViewById(R.id.edit_profile_login_editView);
        mEditMail = editProfileFragment.findViewById(R.id.edit_profile_mail_editView);
        mEditBirthday = editProfileFragment.findViewById(R.id.edit_profile_birthday_editView);

        mChangePassword = editProfileFragment.findViewById(R.id.change_password_textView);
        mEditProfilePhoto = editProfileFragment.findViewById(R.id.edit_profile_profile_photo_imageView);

        mSubmitNameButton = editProfileFragment.findViewById(R.id.edit_profile_name_button);
        mSubmitLoginButton = editProfileFragment.findViewById(R.id.edit_profile_login_button);
        mSubmitProfilePhotoButton = editProfileFragment.findViewById(R.id.edit_profile_profile_image_button);
        mSubmitMailButton = editProfileFragment.findViewById(R.id.edit_profile_mail_button);
        mSubmitBirthdayButton = editProfileFragment.findViewById(R.id.edit_profile_birthday_button);

        mSubmitNameButton.setOnClickListener(view -> submitUserName());
        mSubmitLoginButton.setOnClickListener(view -> submitUserLogin());
        mEditProfilePhoto.setOnClickListener(view -> choosePhotoFromGallery());
        mSubmitProfilePhotoButton.setOnClickListener(view -> submitUserProfilePhoto());
        mSubmitMailButton.setOnClickListener(view -> submitUserMail());
        mSubmitBirthdayButton.setOnClickListener(view -> submitUserBirthday());

        mEditBirthday.setOnClickListener(view -> editBirthdayDay());
        mChangePassword.setOnClickListener(view -> changePassword());

        initializeFields();

        return editProfileFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editProfileFragment = null;
    }

    private void initializeFields() {
        mEditName.setText(mUser.getName());
        mEditLogin.setText(mUser.getLogin());
        mEditMail.setText(mUser.getMail());
        mEditBirthday.setText(new SimpleDateFormat("dd.MM.yyyy").format(mUser.getBirthdayDate()));
        setProfilePhoto();
    }

    private void setProfilePhoto() {
        String profileImageBitmapString = mUser.getProfilePhotoLink();
        Bitmap profileImageBitmap = convertStringToBitMap(profileImageBitmapString);
        mEditProfilePhoto.setImageBitmap(profileImageBitmap);
    }

    private void changePassword() {
        Intent changePasswordIntent = new Intent(getContext(), ChangePasswordActivity.class);
        startActivity(changePasswordIntent);
    }

    private void submitUserName() {
        String name = mEditName.getText().toString().trim();
        if (!validateNameString(name)) {
            Toast.makeText(getContext(), "Entered name is incorrect.", Toast.LENGTH_SHORT).show();
        } else {
            userRef = getFirebaseDatabase().getReference(mUser.getLogin());
            mUser.setName(name);
            CurrentUser.setUser(mUser, true);
            userRef.setValue(mUser).addOnSuccessListener(unused ->
                    Toast.makeText(getContext(), "Successfully changed your name!", Toast.LENGTH_SHORT).show());
        }
    }

    private void submitUserLogin() {
        String login = mEditLogin.getText().toString().trim();
        if (!validateLoginString(login)) {
            Toast.makeText(getContext(), "Entered login is incorrect.", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference enteredLoginUser = getFirebaseDatabase().getReference(login);
        enteredLoginUser.get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.exists()) {
                Toast.makeText(getContext(), "User with entered login is already exists", Toast.LENGTH_SHORT).show();
            } else {
                userRef = getFirebaseDatabase().getReference(mUser.getLogin());
                userRef.removeValue();
                mUser.setLogin(login);
                CurrentUser.setUser(mUser, true);
                enteredLoginUser.setValue(mUser);
                Toast.makeText(getContext(), "Successfully changed your login!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void choosePhotoFromGallery() {
        CropImage.activity()
                .setAspectRatio(1, 1)
                .start(getContext(), this);
    }

    private void submitUserProfilePhoto() {
        Bitmap photoBitmap = ((BitmapDrawable) mEditProfilePhoto.getDrawable()).getBitmap();
        String profileImageBitmapString = convertBitmapToString(photoBitmap);
        userRef = getFirebaseDatabase().getReference(mUser.getLogin());
        mUser.setProfilePhotoLink(profileImageBitmapString);
        CurrentUser.setUser(mUser, true);
        userRef.setValue(mUser).addOnSuccessListener(unused ->
                Toast.makeText(getContext(), "Successfully changed your profile photo!", Toast.LENGTH_SHORT).show());
        CurrentUser.setUser(mUser, true);
    }

    private void submitUserMail() {
        String mail = mEditMail.getText().toString().trim();
        if (!validateMailString(mail)) {
            Toast.makeText(getContext(), "Entered mail is incorrect.", Toast.LENGTH_SHORT).show();
        } else {
            userRef = getFirebaseDatabase().getReference(mUser.getLogin());
            mUser.setMail(mail);
            CurrentUser.setUser(mUser, true);
            userRef.setValue(mUser).addOnSuccessListener(unused ->
                    Toast.makeText(getContext(), "Successfully changed your mail!", Toast.LENGTH_SHORT).show());
            CurrentUser.setUser(mUser, true);
        }
    }

    private void submitUserBirthday() {
        userRef = getFirebaseDatabase().getReference(mUser.getLogin());
        mUser.setBirthdayDate(birthdayDate);
        CurrentUser.setUser(mUser, true);
        userRef.setValue(mUser).addOnSuccessListener(unused ->
                Toast.makeText(getContext(), "Successfully changed your birthday date!", Toast.LENGTH_SHORT).show());
        CurrentUser.setUser(mUser, true);
    }

    private void editBirthdayDay() {
        FragmentManager manager = getFragmentManager();
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setTargetFragment(this, BIRTHDAY_PICKER_REQUEST_CODE);

        Bundle args = new Bundle();
        args.putLong(INITIAL_BIRTHDAY_DATE, birthdayDate);
        datePickerFragment.setArguments(args);

        datePickerFragment.show(manager, "Choose birthday date:");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case BIRTHDAY_PICKER_REQUEST_CODE:
                    birthdayDate = data.getLongExtra(CHOOSE_BIRTHDAY_DATE, 0);
                    mEditBirthday.setText(new SimpleDateFormat("dd.MM.yyyy").format(birthdayDate));
                    break;
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri resultUri = result.getUri();
                    mEditProfilePhoto.setImageURI(resultUri);
                    break;
            }
        }
    }
}