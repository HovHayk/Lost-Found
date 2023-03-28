package com.example.LostFound.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.LostFound.Fragments.MyFoundPostsFragment;
import com.example.LostFound.Fragments.MyLostPostsFragment;
import com.example.LostFound.Models.LostPost;
import com.example.LostFound.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyPostsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    List<LostPost> list;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    EditText nameRegister;
    Button newPost;
    TextView name, email;
    View view, registerView;

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.btmNavView);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        view = navigationView.getHeaderView(0);
        toolbar = findViewById(R.id.toolbar);
        registerView = getLayoutInflater().inflate(R.layout.activity_register, null);
        newPost = findViewById(R.id.btnNewPost);
        recyclerView = findViewById(R.id.recyclerView);
        name = view.findViewById(R.id.personName);
        email = view.findViewById(R.id.person_email);
        nameRegister = registerView.findViewById(R.id.inputUsernameForRegistration);

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();
        list = new ArrayList<LostPost>();

        statusBarColor();
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);

        setBottomNavigationView();

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPostsActivity.this, PostTypeActivity.class);
                startActivity(intent);
            }
        });


        setFragment(new MyLostPostsFragment());
        nameEmailPhotoSetter();


    } // End of onCreate


    public void setBottomNavigationView() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btmnav_lost:
                        setFragment(new MyLostPostsFragment());
                        return true;
                    case R.id.btmnav_found:
                        setFragment(new MyFoundPostsFragment());
                        return true;
                    default:
                        return false;

                }
            }
        });

    }



    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

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
                Intent intentHome = new Intent(MyPostsActivity.this, HomeActivity.class);
                startActivity(intentHome);
            case R.id.nav_profile:
                Intent intentProfile = new Intent(MyPostsActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
            case R.id.nav_myPosts:
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
//  {  Getting information from user's google email


        /*GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            name.setText(personName);
            email.setText(personEmail);
        }*/


//  }


//  SignOut Buttons

   /*public void signOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(MyPostsActivity.this, LoginActivity.class));
            }
        });
    }


}*/