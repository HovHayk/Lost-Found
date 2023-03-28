package com.example.LostFound.NewPost;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LostFound.Activities.HomeActivity;
import com.example.LostFound.Activities.MyPostsActivity;
import com.example.LostFound.Maps.FoundMapActivity;
import com.example.LostFound.Activities.ProfileActivity;
import com.example.LostFound.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class NewPostFoundActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView myLocation;
    EditText postName, postDescription;
    Button addNewPots, btnLocation;
    ImageView setImage;

    private static final int GALLERY_CODE = 1;
    private static final String TAG = "HomeActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private List<String> itemTags;
    private List<String> finalTags;
    private ArrayList<String> tags = new ArrayList<>();

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ProgressDialog progressDialog;

    MultiAutoCompleteTextView postTags;
    private ArrayList<String> postTagsList = new ArrayList<>();

    Uri imageUrl = null;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        toolbar = findViewById(R.id.toolbar);

        postTags = findViewById(R.id.post_tags);

        addNewPots = findViewById(R.id.btnAddPost);
        postName = findViewById(R.id.post_name);
        btnLocation = findViewById(R.id.btn_location);
        myLocation = findViewById(R.id.post_location);
        postDescription = findViewById(R.id.post_description);
        setImage = findViewById(R.id.post_image);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
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

        tagsAutoComplete();

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
                sendUserToNextActivity();
            }
        });

        if (isServicesOK()) {
            init();
        }

        Intent intent = getIntent();
        String userLocation = intent.getStringExtra("myFoundLocation");
        myLocation.setText(userLocation);

    } // End of OnCreate !!!!!!!!!!!


    private void init() {
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPostFoundActivity.this, FoundMapActivity.class);
                startActivity(intent);
            }
        });
    }


    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version ");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(NewPostFoundActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            // there is an error but you can fix it
            Log.d(TAG, "isServicesOK: There is an error but you can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(NewPostFoundActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(NewPostFoundActivity.this, "You can't make map requests", Toast.LENGTH_SHORT).show();
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
        String id = mAuth.getCurrentUser().getUid();
        String name = postName.getText().toString().trim();
        String description = postDescription.getText().toString().trim();
        String location = myLocation.getText().toString().trim();

        if (!(name.isEmpty() && description.isEmpty())) {

            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference filePath = firebaseStorage.getReference().child("images").child(imageUrl.toString());
            filePath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String tags = postTags.getText().toString();
                    itemTags = Arrays.asList(tags.split(","));
                    int changes = -1;
                    while (changes != 0) {
                        changes = 0;
                        finalTags = new ArrayList<>();
                        for (int i = 0; i < itemTags.size(); i++) {
                            if (itemTags.get(i).startsWith(" ") || itemTags.get(i).equals("")) {
                                changes++;
                            }
                            if (!itemTags.get(i).equals("")) {
                                String a = itemTags.get(i).replaceFirst(" ", "");
                                finalTags.add(a);
                            }
                        }
                        itemTags = finalTags;
                    }

                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String t = task.getResult().toString();

                            HashMap<String, Object> post = new HashMap<>();
                            post.put("id", id);
                            post.put("name", name);
                            post.put("description", description);
                            post.put("location", location);
                            post.put("tags", itemTags);
                            post.put("image", t);
                            firebaseFirestore.collection("Found LostPost").add(post);
                            progressDialog.dismiss();
                        }
                    });
                }
            });
        }
    }

    private void tagsAutoComplete() {

        firebaseFirestore.collection("Tags")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            tags.add(snapshot.get("tag").toString());
                        }

                        ArrayAdapter<String> tagArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, tags);
                        postTags.setAdapter(tagArrayAdapter);
                        postTags.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intentHome = new Intent(NewPostFoundActivity.this, HomeActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_profile:
                Intent intentProfile = new Intent(NewPostFoundActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                break;
            case R.id.nav_myPosts:
                Intent intentMyPosts = new Intent(NewPostFoundActivity.this, MyPostsActivity.class);
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
        window.setStatusBarColor(this.getResources().getColor(R.color.colorLightGrey));
    }

    public void sendUserToNextActivity() {
        Intent intent = new Intent(NewPostFoundActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}