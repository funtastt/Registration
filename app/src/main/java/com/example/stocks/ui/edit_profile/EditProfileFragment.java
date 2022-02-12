package com.example.stocks.ui.edit_profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.stocks.CurrentUser;
import com.example.stocks.R;
import com.example.stocks.User;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class EditProfileFragment extends Fragment {

    private View editProfileFragment;
    private EditText mEditName, mEditLogin, mEditMail, mEditBirthday;
    private TextView mChangePassword;
    private Button mSubmitNameButton, mSubmitLoginButton, mSubmitProfilePhotoButton, mSubmitMailButton, mSubmitBirthdayButton;
    private ImageView mEditProfilePhoto;

    User mUser = CurrentUser.getUser();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("Error", "onCreateView()");
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

        mEditBirthday.setOnClickListener(view -> {
            FragmentManager manager = getChildFragmentManager();
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(manager, "Choose birthday date:");
        });

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
//        mEditBirthday.setText("lol");
    }
}