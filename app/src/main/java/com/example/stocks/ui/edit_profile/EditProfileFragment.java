package com.example.stocks.ui.edit_profile;

import static android.app.Activity.RESULT_OK;
import static com.example.stocks.Constants.BIRTHDAY_PICKER_REQUEST_CODE;
import static com.example.stocks.Constants.CHOOSE_BIRTHDAY_DATE;
import static com.example.stocks.Constants.INITIAL_BIRTHDAY_DATE;
import static com.example.stocks.Constants.convertBitmapToString;
import static com.example.stocks.Constants.convertStringToBitMap;
import static com.example.stocks.Constants.currentUserLogin;
import static com.example.stocks.Constants.getFirebaseDatabase;
import static com.example.stocks.Constants.updateInfo;
import static com.example.stocks.Constants.validateLoginString;
import static com.example.stocks.Constants.validateMailString;
import static com.example.stocks.Constants.validateNameString;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.stocks.R;
import com.example.stocks.User;
import com.example.stocks.sqlite.UserCredentialsDatabaseHandler;
import com.google.firebase.database.DatabaseReference;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;


public class EditProfileFragment extends Fragment {
    private View editProfileFragment;
    private EditText mEditName, mEditLogin, mEditMail, mEditBirthday;
    private TextView mChangePassword;
    private Button mSubmitNameButton, mSubmitLoginButton, mSubmitProfilePhotoButton, mSubmitMailButton, mSubmitBirthdayButton;
    private ImageView mEditProfilePhoto;
    private String currentUserProfileImageBitmapString;

    private User currentUser;
    private DatabaseReference userRef;

    private UserCredentialsDatabaseHandler mHandler;

    private long birthdayDate;


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

        mHandler = new UserCredentialsDatabaseHandler(getContext());
        currentUser = mHandler.getUser();
        birthdayDate = currentUser.getBirthdayDate();

        initializeFields();

        return editProfileFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editProfileFragment = null;
    }

    private void initializeFields() {
        mEditName.setText(currentUser.getName());
        mEditLogin.setText(currentUser.getLogin());
        mEditMail.setText(currentUser.getMail());
        mEditBirthday.setText(new SimpleDateFormat("dd.MM.yyyy").format(currentUser.getBirthdayDate()));
        setProfilePhoto();
    }

    private void setProfilePhoto() {
        String profileImageBitmapString = currentUser.getProfilePhotoLink();
        currentUserProfileImageBitmapString = currentUser.getProfilePhotoLink();
        Bitmap profileImageBitmap = convertStringToBitMap(profileImageBitmapString);
        mEditProfilePhoto.setImageBitmap(profileImageBitmap);
    }

    // Todo: Implement password change logic
    private void changePassword() {
        Intent changePasswordIntent = new Intent(getContext(), ChangePasswordActivity.class);
        startActivity(changePasswordIntent);
    }

    private void submitUserName() {
        String name = mEditName.getText().toString().trim();
        String currentUserName = currentUser.getName();
        if (name.equals(currentUserName)) return;
        if (!validateNameString(name)) {
            Toast.makeText(getContext(), "Entered name is incorrect.", Toast.LENGTH_SHORT).show();
        } else {
            userRef = getFirebaseDatabase().getReference(currentUser.getLogin());
            currentUser.setName(name);
            afterSuccessfulSubmittingChanges();
        }
    }

    private void submitUserLogin() {
        String login = mEditLogin.getText().toString().trim();
        if (!validateLoginString(login)) {
            Toast.makeText(getContext(), "Entered login is incorrect.", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference changedLoginUserRef = getFirebaseDatabase().getReference(login);
        changedLoginUserRef.get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.exists()) {
                Toast.makeText(getContext(), "User with entered login is already exists", Toast.LENGTH_SHORT).show();
            } else {
                userRef = getFirebaseDatabase().getReference(currentUser.getLogin());
                userRef.removeValue();

                currentUserLogin = login;

                currentUser.setLogin(login);
                mHandler.updateUser(currentUser);
                changedLoginUserRef.setValue(currentUser).addOnSuccessListener(unused ->
                        Toast.makeText(getContext(), "Successfully changed your data", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void choosePhotoFromGallery() {
        CropImage.activity()
                .setAspectRatio(1, 1)
                .start(getContext(), this);
    }

    private void submitUserProfilePhoto() {
        Bitmap profileImageBitmap = ((BitmapDrawable) mEditProfilePhoto.getDrawable()).getBitmap();
        String profileImageBitmapString = convertBitmapToString(profileImageBitmap);
        if (profileImageBitmapString.equals(currentUserProfileImageBitmapString)) {
            return;
        } else {
            currentUserProfileImageBitmapString = profileImageBitmapString;
        }
        userRef = getFirebaseDatabase().getReference(currentUser.getLogin());
        currentUser.setProfilePhotoLink(profileImageBitmapString);
        afterSuccessfulSubmittingChanges();
    }

    private void submitUserMail() {
        String mail = mEditMail.getText().toString().trim();
        String currentUserMail = currentUser.getMail();
        if (mail.equals(currentUserMail)) return;
        if (!validateMailString(mail)) {
            Toast.makeText(getContext(), "Entered mail is incorrect.", Toast.LENGTH_SHORT).show();
        } else {
            userRef = getFirebaseDatabase().getReference(currentUser.getLogin());
            currentUser.setMail(mail);
            afterSuccessfulSubmittingChanges();
        }
    }

    private void submitUserBirthday() {
        long currentUserBirthdayDate = currentUser.getBirthdayDate();
        if (currentUserBirthdayDate == birthdayDate) return;
        userRef = getFirebaseDatabase().getReference(currentUser.getLogin());
        currentUser.setBirthdayDate(birthdayDate);
        afterSuccessfulSubmittingChanges();
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

    private void afterSuccessfulSubmittingChanges() {
        mHandler.updateUser(currentUser);
        userRef.setValue(currentUser).addOnSuccessListener(unused ->
                Toast.makeText(getContext(), "Successfully changed your data", Toast.LENGTH_SHORT).show());
        currentUser = mHandler.getUser();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateInfo(mHandler);
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