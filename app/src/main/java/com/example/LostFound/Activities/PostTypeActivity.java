package com.example.LostFound.Activities;

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
import android.widget.TextView;

import com.example.LostFound.NewPost.NewPostFoundActivity;
import com.example.LostFound.NewPost.NewPostLostActivity;
import com.example.LostFound.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class PostTypeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button btnLost, btnFound;
    TextView emailUser, nameUser;
    View view;


    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_type);

        btnLost = findViewById(R.id.btn_post_type_lost);
        btnFound = findViewById(R.id.btn_post_type_found);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        view = navigationView.getHeaderView(0);
        nameUser = view.findViewById(R.id.person_name);
        emailUser = view.findViewById(R.id.person_email);

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();

        nameEmailPhotoSetter();
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
                Intent intentHome = new Intent(PostTypeActivity.this, HomeActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_profile:
                Intent intentProfile = new Intent(PostTypeActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                break;
            case R.id.nav_myPosts:
                Intent intentMyPosts = new Intent(PostTypeActivity.this, MyPostsActivity.class);
                startActivity(intentMyPosts);
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
        window.setStatusBarColor(this.getResources().getColor(R.color.light_grey));
    }

    public void nameEmailPhotoSetter() {

        String uEmail = auth.getCurrentUser().getEmail();
        emailUser.setText(uEmail);

        if (auth.getCurrentUser().getDisplayName() == null) {
            firebaseFirestore.collection("Users").whereEqualTo("email", uEmail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        if (snapshot.exists()) {
                            nameUser.setText(snapshot.get("user").toString());
                        }
                    }
                }
            });
        } else {
            nameUser.setText(auth.getCurrentUser().getDisplayName());
        }
    }

}