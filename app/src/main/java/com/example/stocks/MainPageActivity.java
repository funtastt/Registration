package com.example.stocks;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stocks.databinding.ActivityMainPageBinding;
import com.google.firebase.storage.FileDownloadTask;
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

    private void setProfilePhoto() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        currentUserProfileImage = storage.getReferenceFromUrl("gs://stocks-95f7e.appspot.com/images/" + currentUser.getProfilePhotoLink());
        try {
            final File file = File.createTempFile("currentUserProfileImage", "png");
            currentUserProfileImage.getFile(file).addOnSuccessListener(taskSnapshot -> {
                Bitmap userProfileImageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                userProfileImage.setImageBitmap(userProfileImageBitmap);
            }).addOnFailureListener(e -> Toast.makeText(MainPageActivity.this, "Failure!!!", Toast.LENGTH_SHORT).show());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}