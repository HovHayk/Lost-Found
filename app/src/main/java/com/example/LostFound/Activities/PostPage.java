package com.example.LostFound.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.LostFound.Adapters.TagAdapter;
import com.example.LostFound.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    FirebaseAuth auth;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View view;

    String uid, uEmail;

    TextView name, email, postName, postLocation, postDescription;
    ImageView postImage;
    RecyclerView tagsRecyclerView;
    ArrayList<String> tags;
    ShapeableImageView creatorPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        toolbar = findViewById(R.id.toolbar);

        view = navigationView.getHeaderView(0);
        name = view.findViewById(R.id.personName);
        email = view.findViewById(R.id.person_email);
        postName = findViewById(R.id.post_name);
        postLocation = findViewById(R.id.post_location);
        postDescription = findViewById(R.id.post_description);
        postImage = findViewById(R.id.post_image);
        tagsRecyclerView = findViewById(R.id.tags_recycler_view);
        creatorPhoto = findViewById(R.id.creator_image);

        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        statusBarColor();
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);


        setPostData();
        nameEmailPhotoSetter();
    }

    private void setPostData() {

        Picasso.get().load(getIntent().getStringExtra("IMAGE")).into(postImage);
        postName.setText(getIntent().getStringExtra("NAME"));
        postDescription.setText(getIntent().getStringExtra("DESCRIPTION"));
        postLocation.setText(getIntent().getStringExtra("LOCATION"));

        uid = getIntent().getStringExtra("UID");
        uEmail = getIntent().getStringExtra("UEMAIL");

        tags = new ArrayList<>();
        tags.add(getIntent().getStringExtra("TAGS"));

        TagAdapter tagAdapter = new TagAdapter(tags);
        tagsRecyclerView.setAdapter(tagAdapter);
        tagsRecyclerView.setVisibility(View.VISIBLE);

        creatorPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostPage.this, ProfileActivity.class);
                intent.putExtra("UID", uid);
                intent.putExtra("UEMAIL", uEmail);
                startActivity(intent);
            }
        });
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
                Intent intentHome = new Intent(PostPage.this, HomeActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_profile:
                Intent intentProfile = new Intent(PostPage.this, ProfileActivity.class);
                startActivity(intentProfile);
                break;
            case R.id.nav_myPosts:
                Intent intentMyPosts = new Intent(PostPage.this, MyPostsActivity.class);
                startActivity(intentMyPosts);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void statusBarColor() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.light_grey));
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


}