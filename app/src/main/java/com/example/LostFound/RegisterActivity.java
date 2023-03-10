package com.example.LostFound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.Login;
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


public class RegisterActivity extends AppCompatActivity {

    LinearLayout main;

    EditText userNameForRegistration, emailForRegistration, phoneForRegistration, cityForRegistration, passwordForRegistration, confirmPassword;
    TextView haveAcc;
    Button signUp;

    ProgressDialog progressDialog;

    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private final String phonePattern = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;


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

        progressDialog = new ProgressDialog(RegisterActivity.this);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        main = findViewById(R.id.register_main);

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
                performAuth();
                sendUserInfoToLoginActivity();
            }
        });



        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });





    } // End of OnCreate



    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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


    private void performAuth() {
        String user = userNameForRegistration.getText().toString();
        String email = emailForRegistration.getText().toString();
        String phone = phoneForRegistration.getText().toString();
        String city = cityForRegistration.getText().toString();
        String password = passwordForRegistration.getText().toString();
        String passwordConfirm = confirmPassword.getText().toString();


        if (!email.matches(emailPattern)) {
            emailForRegistration.setError("Enter Context Email");
        } else if (password.isEmpty() || password.length() < 6) {
            passwordForRegistration.setError("Enter Proper Password");
        } else if (user.equals("")) {
            userNameForRegistration.setError("Please enter username");
        } else if (city.equals("")) {
            cityForRegistration.setError("Please enter your city");
        } else if (!password.equals(passwordConfirm)) {
            confirmPassword.setError("Password Not match Both field  ");
        } else if (!phone.matches(phonePattern)) {
            phoneForRegistration.setError("Please enter correct number");
        } else {

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.setTitle("Creating new user");
                                    progressDialog.dismiss();
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


    private void sendUserInfoToLoginActivity() {
        String name = userNameForRegistration.getText().toString().trim();
        String city = cityForRegistration.getText().toString().trim();
        String email = emailForRegistration.getText().toString().trim();
        String phone = phoneForRegistration.getText().toString().trim();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("NAME", name);
        intent.putExtra("CITY", city);
        intent.putExtra("EMAIL", email);
        intent.putExtra("PHONE", phone);
        startActivity(intent);
    }


    private void sendUserToNextActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}