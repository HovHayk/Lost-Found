package com.example.LostFound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class PostTypeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button btnLost, btnFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_type);

        btnLost = findViewById(R.id.btnPostTypeLost);
        btnFound = findViewById(R.id.btnPostTypeFound);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        toolbar = findViewById(R.id.toolbar);
        statusBarColor();
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);


        btnLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPostLostActivity();
            }
        });


        btnFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPostFoundActivity();
            }
        });


    } // End of OnCreate !!!!!!!!!!!!!!!!!!


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intentHome = new Intent(PostTypeActivity.this, MainActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_profile:
                Intent intentProfile = new Intent(PostTypeActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openNewPostFoundActivity() {
        Intent intent = new Intent(PostTypeActivity.this, NewPostFoundActivity.class);
        startActivity(intent);
    }

    public void openNewPostLostActivity() {
        Intent intent = new Intent(PostTypeActivity.this, NewPostLostActivity.class);
        startActivity(intent);
    }

    public void statusBarColor() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorLightGrey));
    }

}