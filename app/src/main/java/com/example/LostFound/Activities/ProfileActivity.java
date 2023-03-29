package com.example.LostFound.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.LostFound.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button newPost;
    TextView namePhoto, name, email, phone, city;
    String id;

    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        toolbar = findViewById(R.id.toolbar);

        namePhoto = findViewById(R.id.persons_name);
        email = findViewById(R.id.persons_email);
        name = findViewById(R.id.personName);
        phone = findViewById(R.id.personPhone);
        city = findViewById(R.id.personCity);


        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        id = auth.getCurrentUser().getUid();
        setUserInfo(id);
        nameEmailPhotoSetter();


        statusBarColor();
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);


    } // End of OnCreate !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

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
                Intent intentHome = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_profile:
                break;
            case R.id.nav_myPosts:
                Intent intentMyPosts = new Intent(ProfileActivity.this, MyPostsActivity.class);
                startActivity(intentMyPosts);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setUserInfo(String id) {

        String uEmail = auth.getCurrentUser().getEmail();

        String uid = getIntent().getStringExtra("UID");
        String UEmail = getIntent().getStringExtra("UEMAIL");

        if (UEmail == null) {

            firebaseFirestore.collection("Users").whereEqualTo("email", uEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            name.setText(snapshot.get("user").toString());
                            namePhoto.setText(snapshot.get("user").toString());
                            email.setText(snapshot.get("email").toString());
                            city.setText(snapshot.get("city").toString());
                            phone.setText(snapshot.get("phone").toString());
                        }
                    }
                }
            });

        } else {

            firebaseFirestore.collection("Users").whereEqualTo("email", UEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot snapshot : task.getResult()) {

                            if (snapshot.getString("city") != null) {

                                name.setText(snapshot.get("user").toString());
                                namePhoto.setText(snapshot.get("user").toString());
                                email.setText(snapshot.get("email").toString());
                                city.setText(snapshot.get("city").toString());
                                phone.setText(snapshot.get("phone").toString());
                            } else {
                                name.setText("Please edit your Profile");
                                namePhoto.setText("Please edit your Profile");
                                email.setText("Please edit your Profile");
                                city.setText("Please edit your Profile");
                                phone.setText("Please edit your Profile");
                            }
                        }
                    }
                }
            });
        }
    }

    public void nameEmailPhotoSetter() {

        String uEmail = auth.getCurrentUser().getEmail();
        email.setText(uEmail);

        if (auth.getCurrentUser().getDisplayName() == null) {
            firebaseFirestore.collection("Users").whereEqualTo("email", uEmail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        if (snapshot.exists()) {
                            name.setText(snapshot.get("user").toString());
                        }
                    }
                }
            });
        } else {
            name.setText(auth.getCurrentUser().getDisplayName());
        }
    }


    public void statusBarColor() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorLightGrey));
    }
}