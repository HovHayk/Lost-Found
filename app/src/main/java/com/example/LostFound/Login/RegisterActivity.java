package com.example.LostFound.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.LostFound.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


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

    FirebaseFirestore firebaseFirestore;
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
        firebaseFirestore = FirebaseFirestore.getInstance();

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
                sendUserToNextActivity();
            }
        });



        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
            }
        });


        statusBarColor();


    } // End of OnCreate



    private void performAuth() {
        String uName = userNameForRegistration.getText().toString();
        String email = emailForRegistration.getText().toString();
        String phone = phoneForRegistration.getText().toString();
        String city = cityForRegistration.getText().toString();
        String password = passwordForRegistration.getText().toString();
        String passwordConfirm = confirmPassword.getText().toString();


        if (!email.matches(emailPattern)) {
            emailForRegistration.setError("Enter Context Email");
        } else if (password.isEmpty() || password.length() < 6) {
            passwordForRegistration.setError("Enter Proper Password");
        } else if (uName.equals("")) {
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

                                    HashMap<String,Object> user = new HashMap<>();
                                    user.put("user",uName);
                                    user.put("email",email);
                                    user.put("phone",phone);
                                    user.put("city",city);

                                    firebaseFirestore.collection("Users").add(user);
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

    public void statusBarColor() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.light_grey));
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
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