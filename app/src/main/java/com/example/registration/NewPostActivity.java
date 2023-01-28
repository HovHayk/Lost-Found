package com.example.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.registration.databinding.ActivityMainBinding;
import com.example.registration.databinding.ActivityNewPostBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewPostActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText postName, postPlace, postDescription;
    Spinner categories;
    Button addNewPots;
    ImageButton setImage;

    private static final int GALLERY_CODE = 1;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    ProgressDialog progressDialog;

    Uri imageUrl = null;
//    ActivityNewPostBinding binding;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    DatabaseReference postsDBRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        /*binding = ActivityNewPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        toolbar = findViewById(R.id.toolbar);


        categories = findViewById(R.id.spinnerCategory);
        addNewPots = findViewById(R.id.btnAddPost);
        postName = findViewById(R.id.postName);
        postPlace = findViewById(R.id.postPlace);
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


    } // End of OnCreate !!!!!!!!!!!



    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_CODE);
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





















    /*private void insertPostData() {

        String name = postName.getText().toString();
        String place = postPlace.getText().toString();
        String description = postDescription.getText().toString();
        String category = categories.getSelectedItem().toString();
        String image = imageURI.toString();


        NewPost newPost = new NewPost(name, place, description, category, image);

        postsDBRef.push().setValue(newPost);
        Toast.makeText(this, "Data inserted successfuly!", Toast.LENGTH_SHORT).show();
    }


    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,100);
    }

    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading is in progress...");
        progressDialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);

        storageReference = FirebaseStorage.getInstance().getReference().child("imagePost").child(imageURI.getPath());


        storageReference.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String t = task.getResult().toString();

                        DatabaseReference newPost = postsDBRef.push();

                        newPost.child("Name").setValue(postName);
                        newPost.child("Place").setValue(postPlace);
                        newPost.child("Description").setValue(postDescription);
                        newPost.child("Image").setValue(task.getResult().toString());
                        newPost.child("Category").setValue(categories.getSelectedItem().toString());
                        progressDialog.dismiss();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(NewPostActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK) {
            imageURI = data.getData();
            setImage.setImageURI(imageURI);
        }
    }*/

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