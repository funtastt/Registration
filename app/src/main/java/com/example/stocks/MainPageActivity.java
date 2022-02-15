package com.example.stocks;

import static com.example.stocks.Constants.convertStringToBitMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.stocks.databinding.ActivityMainPageBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MainPageActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ImageView userProfileImage;
    TextView userName;
    private ActivityMainPageBinding binding;
    User currentUser;
    StorageReference currentUserProfileImage;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.drawerLayout.findViewById(R.id.toolbar));
        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_change_profile, R.id.nav_market, R.id.nav_top_up)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        View headerView = navigationView.getHeaderView(0);
        userProfileImage = headerView.findViewById(R.id.user_profile_image);
        userName = headerView.findViewById(R.id.user_name);
        currentUser = CurrentUser.getUser();
        userName.setText(currentUser.getName());
        setProfilePhoto();
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setProfilePhoto() {
        String profileImageBitmapString = currentUser.getProfilePhotoLink();;
        Bitmap profileImageBitmap = convertStringToBitMap(profileImageBitmapString);
        userProfileImage.setImageBitmap(profileImageBitmap);
    }
}