package com.example.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class NewPostActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText postName, postPlace, postDescription;
    Spinner categories;
    Button addNewPots, btnLocation;
    ImageButton setImage;

    private static final int GALLERY_CODE = 1;

    private static final String TAG = "MainActivity";
    private static  final int ERROR_DIALOG_REQUEST = 9001;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ProgressDialog progressDialog;

    Uri imageUrl = null;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    DatabaseReference postsDBRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        toolbar = findViewById(R.id.toolbar);


        categories = findViewById(R.id.spinnerCategory);
        addNewPots = findViewById(R.id.btnAddPost);
        postName = findViewById(R.id.postName);
        btnLocation = findViewById(R.id.btnLocation);
        postDescription = findViewById(R.id.postDescription);
        setImage = findViewById(R.id.postImage);

        progressDialog = new ProgressDialog(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        postsDBRef = firebaseDatabase.getReference().child("Posts");


        statusBarColor();
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);


        setImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        addNewPots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPostData();
            }
        });

        if (isServicesOK()) {
            init();
        }


    } // End of OnCreate !!!!!!!!!!!


    private void init() {
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPostActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }


    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version ");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(NewPostActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            // there is an error but you can fix it
            Log.d(TAG, "isServicesOK: There is an error but you can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(NewPostActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(NewPostActivity.this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {

            imageUrl = data.getData();
            setImage.setImageURI(imageUrl);
        }
    }

    public void insertPostData() {
        String name = postName.getText().toString().trim();
        String place = postPlace.getText().toString().trim();
        String description = postDescription.getText().toString().trim();
        String category = categories.getSelectedItem().toString().trim();

        if (!(name.isEmpty() && place.isEmpty() && description.isEmpty() && category.isEmpty() && imageUrl != null)) {

            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference filePath = firebaseStorage.getReference().child("images").child(imageUrl.toString());
            filePath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String t = task.getResult().toString();

                            DatabaseReference newPost = postsDBRef.push();
                            newPost.child("Name").setValue(name);
                            newPost.child("Place").setValue(place);
                            newPost.child("Description").setValue(description);
                            newPost.child("Category").setValue(category);
                            newPost.child("image").setValue(task.getResult().toString());
                            progressDialog.dismiss();
                        }
                    });
                }
            });
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