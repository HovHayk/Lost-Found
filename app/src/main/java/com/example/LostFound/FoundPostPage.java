package com.example.LostFound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class FoundPostPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    DatabaseReference postsDBRef;
    FirebaseAuth mAuth;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View view;

    TextView name, email, postName, postLocation, postDescription;
    ImageView postImage;
    List<Posts> list;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        toolbar = findViewById(R.id.toolbar);


        view = navigationView.getHeaderView(0);
        name = view.findViewById(R.id.personName);
        email = view.findViewById(R.id.personEmail);
        postName = findViewById(R.id.post_name);
        postLocation = findViewById(R.id.post_location);
        postDescription = findViewById(R.id.post_description);
        postImage = findViewById(R.id.post_image);

        list = new ArrayList<Posts>();

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child("Found");

        statusBarColor();
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);

        id = mAuth.getCurrentUser().getUid();
        setPostInfo(id);
        nameEmailPhotoSetter();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_profile:
                Intent intent = new Intent(FoundPostPage.this, ProfileActivity.class);
                startActivity(intent);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setPostInfo(String id) {

        databaseReference.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {

                    DataSnapshot snapshot = task.getResult();

                    Posts posts = new Posts(snapshot.child("Name").getValue().toString()
                            , snapshot.child("Location").getValue().toString()
                            , snapshot.child("Description").getValue().toString()
                            , snapshot.child("image").getValue().toString());



                    postImage.setImageURI(Uri.parse(posts.getImage()));
                    postName.setText(posts.getName());
                    postLocation.setText(posts.getLocation());
                    postDescription.setText(posts.getDescription());


                }

            }
        });
    }

    public void statusBarColor() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorLightGrey));
    }

    public void nameEmailPhotoSetter() {
        email.setText(mAuth.getCurrentUser().getEmail());
        name.setText(mAuth.getCurrentUser().getDisplayName());
    }

}