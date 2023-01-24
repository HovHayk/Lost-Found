package com.example.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class NewPostActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int SELECT_PHOTO = 100;
    ImageView postImage;
    EditText postName, postPlace, postDescription;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        toolbar = findViewById(R.id.toolbar);
        postImage = findViewById(R.id.postImage);


        statusBarColor();
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);



        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(postImage);
            }
        });


    } // End of OnCreate !!!!!!!!!!!



    public void openCamera(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectImage = data.getData();
                InputStream inputStream = null;
                try {
                    assert selectImage != null;
                    inputStream = getContentResolver().openInputStream(selectImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                BitmapFactory.decodeStream(inputStream);
                postImage.setImageURI(selectImage);
            }
        }
    }

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
                Intent intentHome = new Intent(NewPostActivity.this, MainActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_profile:
                Intent intentProfile = new Intent(NewPostActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void statusBarColor() {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorLightGrey));
    }
}