package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity {

    EditText userNameForRegistration, emailForRegistration, phoneForRegistration, cityForRegistration, passwordForRegistration, confirmPassword;
    TextView haveAcc;
    Button signUp, google, facebook;
    private ProgressDialog progressDialog;
    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    DatabaseReference infoDBRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNameForRegistration = findViewById(R.id.inputUsernameForRegistration);
        emailForRegistration = findViewById(R.id.inputEmailForRegistration);
        phoneForRegistration = findViewById(R.id.inputPhoneForRegistration);
        cityForRegistration = findViewById(R.id.inputCityForRegistration);
        passwordForRegistration = findViewById(R.id.inputPasswordForRegistration);
        confirmPassword = findViewById(R.id.inputConfirmPasswordForRegistration);
        signUp = findViewById(R.id.btnRegister);
        haveAcc = findViewById(R.id.alreadyHaveAccount);
        google = findViewById(R.id.btnGoogle);
        facebook = findViewById(R.id.btnFacebook);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        infoDBRef = firebaseDatabase.getReference();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        gsc = GoogleSignIn.getClient(RegisterActivity.this, gso);


        haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perforAuth();
                insertUserData();
            }
        });


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });


    } // End of OnCreate


    void signInGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //mCallbackManger.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                Toast.makeText(RegisterActivity.this, "Sign in Complete ", Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {

                Toast.makeText(RegisterActivity.this, "Authentication Failed Poblems with " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {

        //getting user credentials with the help of AuthCredential method and also passing user Token Id.
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        //trying to sign in user using signInWithCredential and passing above credentials of user.
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(RegisterActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    private void perforAuth() {
        String user = userNameForRegistration.getText().toString();
        String email = emailForRegistration.getText().toString();
        String password = passwordForRegistration.getText().toString();
        String passwordConfirm = confirmPassword.getText().toString();


        if (!email.matches(emailPattern)) {
            emailForRegistration.setError("Enter Context Email");
        } else if (password.isEmpty() || password.length() < 6) {
            passwordForRegistration.setError("Enter Proper Password");
        } else if (user.equals("")) {
            userNameForRegistration.setError("Please enter username");
        } else if (!password.equals(passwordConfirm)) {
            confirmPassword.setError("Password Not match Both field  ");
        } else {
            //can be a problem with progress dialog
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.setTitle("Creating new user");
                                    progressDialog.dismiss();
                                    sendUserToNextActivity();
                                    Toast.makeText(RegisterActivity.this, "Registration Successful. Please verify your email id", Toast.LENGTH_SHORT).show();
                                    emailForRegistration.setText("");
                                    passwordForRegistration.setText("");
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }
                }
            });
        }
    }


    public void insertUserData() {
        String id = mAuth.getUid();
        String name = userNameForRegistration.getText().toString().trim();
        String city = cityForRegistration.getText().toString().trim();
        String email = emailForRegistration.getText().toString().trim();
        String phoneNumber = phoneForRegistration.getText().toString().trim();
        int phone = Integer.parseInt(phoneNumber);

        if (!(name.isEmpty() && city.isEmpty() && phoneNumber.isEmpty() && email.isEmpty())) {

            /*DatabaseReference newUser = infoDBRef.push().child("Users");
            newUser.child("Name").setValue(name);
            newUser.child("City").setValue(city);
            newUser.child("Email").setValue(email);
            newUser.child("Phone").setValue(phone);
            newUser.child("UserID").setValue(id);*/

            UserInfo user = new UserInfo(name, city, id, email, phone);
            infoDBRef.child("Users").push().setValue(user);
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}