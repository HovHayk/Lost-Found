package com.example.LostFound.NewPost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LostFound.Activities.HomeActivity;
import com.example.LostFound.Maps.LostMapActivity;
import com.example.LostFound.Activities.MyPosts;
import com.example.LostFound.Activities.ProfileActivity;
import com.example.LostFound.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;


public class NewPostLostActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    MultiAutoCompleteTextView postTags;
    TextView postLocation;
    EditText postName, postDescription;
    Button addNewPots, btnLocation;
    ImageView setImage;

    private static final int GALLERY_CODE = 1;
    private static final String TAG = "HomeActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private List<String> itemTags;
//    private ArrayList<String> tags = new ArrayList<>();


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ProgressDialog progressDialog;

    Uri imageUrl = null;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReference;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        toolbar = findViewById(R.id.toolbar);

        addNewPots = findViewById(R.id.btnAddPost);
        postName = findViewById(R.id.post_name);
        postLocation = findViewById(R.id.post_location);
        btnLocation = findViewById(R.id.btn_location);
        postDescription = findViewById(R.id.post_description);
        setImage = findViewById(R.id.post_image);
        postTags = findViewById(R.id.post_tags);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child("Lost");

        statusBarColor();
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);

        //tagsAutoComplete();

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
            openMap();
        }

        Intent intent = getIntent();
        String myLocation = intent.getStringExtra("myLostLocation");
        postLocation.setText(myLocation);

    } // End of OnCreate !!!!!!!!!!!


    private void openMap() {
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPostLostActivity.this, LostMapActivity.class);
                startActivity(intent);
            }
        });
    }


    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version ");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(NewPostLostActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            // there is an error but you can fix it
            Log.d(TAG, "isServicesOK: There is an error but you can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(NewPostLostActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(NewPostLostActivity.this, "You can't make map requests", Toast.LENGTH_SHORT).show();
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
        String location = postLocation.getText().toString().trim();
        String tags = postTags.getText().toString();
//        itemTags = Arrays.asList(tags.split(","));

        if (!(name.isEmpty() && description.isEmpty())) {

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

                            DatabaseReference newPost = databaseReference.push();
                            newPost.child("Name").setValue(name);
                            newPost.child("Description").setValue(description);
                            newPost.child("UserID").setValue(id);
                            newPost.child("Location").setValue(location);
                            newPost.child("image").setValue(t);
//                            newPost.child("tags").setValue(itemTags);
                            progressDialog.dismiss();
                        }
                    });
                }
            });
        }

        /*for (int i = 0; i <= itemTags.size(); i++) {

            firebaseFirestore.collection("Tags").add(itemTags.get(i)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    progressDialog.dismiss();
                    Toast.makeText(NewPostLostActivity.this, "Published successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }*/
    }


    /*private void tagsAutoComplete() {

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
                Intent intentHome = new Intent(NewPostLostActivity.this, HomeActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_profile:
                Intent intentProfile = new Intent(NewPostLostActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                break;
            case R.id.nav_myPosts:
                Intent intentMyPosts = new Intent(NewPostLostActivity.this, MyPosts.class);
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

    private void createNotify() {
        String id = "my_idd";
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel(id);
            if (channel == null) {
                channel = new NotificationChannel(id, "channel Title", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("inchvor description");
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 300, 340});
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(channel);
            }
        }
        Intent notificationIntent = new Intent(this, HomeActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(null)
                .setContentTitle("Title")
                .setContentText("Your text description")
                .setVibrate(new long[]{100, 200, 300, 340})
                .setAutoCancel(false)
                .setTicker("Notification");
        builder.setContentIntent(contentIntent);
        NotificationManagerCompat m = NotificationManagerCompat.from(getApplicationContext());
        m.notify(1, builder.build());
    }
}



