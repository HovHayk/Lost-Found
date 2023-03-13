package com.example.LostFound.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LostFound.Activities.HomeActivity;
import com.example.LostFound.Models.UserInfo;
import com.example.LostFound.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    TextView register;
    EditText emailForLogin, passwordForLogin;
    Button logIn, google;

    private ProgressDialog progressDialog;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;

    ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        google = findViewById(R.id.btnGoogle);
        emailForLogin = findViewById(R.id.inputEmail);
        passwordForLogin = findViewById(R.id.inputPassword);
        logIn = findViewById(R.id.btnLogin);
        register = findViewById(R.id.textViewSignUp);
        progressDialog = new ProgressDialog(LoginActivity.this);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        main = findViewById(R.id.login_main);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        gsc = GoogleSignIn.getClient(LoginActivity.this, gso);




        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
                //insertUserData();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });




        // starting app without login
        if (user != null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

    } // End of OnCreate !!!


    public void signInGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                Toast.makeText(LoginActivity.this, "Sign in Complete ", Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {

                Toast.makeText(LoginActivity.this, "Authentication Failed Poblems with " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

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

        Intent notificationIntent = new Intent(this, LoginActivity.class);
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

    private void performLogin() {

        String email = emailForLogin.getText().toString();
        String password = passwordForLogin.getText().toString();

        if (!email.matches(emailPattern)) {
            emailForLogin.setError("Enter Context Email");
        } else if (password.trim().isEmpty() || password.length() < 6) {
            passwordForLogin.setError("Enter Proper Password");
        } else {
            //can be a problem with progress dialog
            progressDialog.setMessage("Please Wait...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (auth.getCurrentUser().isEmailVerified()) {
                            progressDialog.dismiss();
                            sendUserToNextActivity();
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Please verify your email id", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {

        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    public void insertUserData() {

        String id = auth.getCurrentUser().getUid();

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("NAME");
        String city = bundle.getString("CITY");
        String email = bundle.getString("EMAIL");
        String phone = bundle.getString("PHONE");

        UserInfo user = new UserInfo(name, city, id, email, phone);
        databaseReference.child("Users").child(id).setValue(user);

    }


    private void firebaseAuthWithGoogle(String idToken) {

        //getting user credentials with the help of AuthCredential method and also passing user Token Id.
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        //trying to sign in user using signInWithCredential and passing above credentials of user.
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}