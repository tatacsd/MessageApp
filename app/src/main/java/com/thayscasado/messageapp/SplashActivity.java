package com.thayscasado.messageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    final String TAG = "firebaseAUTH";

    // Get the ui references
    private EditText userEmail, userPassword;
    private Button btnSignup, btnLogin;
    private String email;

    FirebaseDatabase mfirebaseIntance;
    DatabaseReference mfirebaseDatabase;

    // References to firebase auth
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextPassword);
        btnSignup = findViewById(R.id.buttonSignup);
        btnLogin = findViewById(R.id.buttonLogin);

        // Get the reference of the firebase
        // the get instance will get all the configurations on the google-service.json
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SplashActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Log.v(TAG, "Email verified? " + auth.getCurrentUser().isEmailVerified());
                        } else {
                            // there was an error
                            Log.v(TAG, "Error loging ");
                        }

                    }
                });

    }

    private void signUp() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Log.v(TAG, "empty email");
            return;
        }

        // Create an account using Firebase auth
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SplashActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If we reach here the auth was complete
                        if (task.isSuccessful()) {
                            // add to database table users
                            mfirebaseIntance = FirebaseDatabase.getInstance();
                            mfirebaseDatabase = mfirebaseIntance.getReference("users");
                            mfirebaseDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String[] userID = auth.getCurrentUser().getEmail().split("@");
                                    // Create a user
                                    mfirebaseDatabase.child(userID[0])
                                            .setValue(auth.getCurrentUser().getEmail());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });

                            // redirect user to main activity
                            Log.v(TAG, "User created");
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.v(TAG, "Fail to create a user");
                            // display error message
                            Log.v(TAG, task.getException().getMessage());
                        }

                    }
                });
    }
}