package com.example.stocks;

import static com.example.stocks.Constants.convertStringToBitMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.stocks.databinding.ActivityMainPageBinding;
import com.google.android.material.navigation.NavigationView;

public class MainPageActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ImageView userProfileImage;
    TextView userNameTextview, logOutTextview;
    private ActivityMainPageBinding binding;
    User currentUser;
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
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        View headerView = navigationView.getHeaderView(0);
        userProfileImage = headerView.findViewById(R.id.user_profile_image);
        userNameTextview = headerView.findViewById(R.id.user_name);
        logOutTextview = navigationView.findViewById(R.id.logout);
        currentUser = CurrentUser.getUser();
        userNameTextview.setText(currentUser.getName());
        logOutTextview.setOnClickListener(view -> logOut());
        setProfilePhoto();
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setProfilePhoto() {
        String profileImageBitmapString = currentUser.getProfilePhotoLink();;
        Bitmap profileImageBitmap = convertStringToBitMap(profileImageBitmapString);
        userProfileImage.setImageBitmap(profileImageBitmap);
    }

    // Todo: Implement the logic of not being able to return to the profile page after logging out
    private void logOut() {
        Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}