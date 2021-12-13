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

import org.w3c.dom.Text;

public class Signup extends AppCompatActivity {
    final String TAG = "firebaseAUTH";

    // Get the ui references
    private EditText userEmail;
    private Button btnSignup;
    private String email;

    // References to firebase auth
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userEmail = findViewById(R.id.editTextAuthEmail);
        btnSignup = findViewById(R.id.buttonSignup);

        email = userEmail.getText().toString().trim();

        // Get the reference of the firebase
        // the get instance will get all the configurations on the google-service.json
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

    }

    private void submitForm() {

        if (TextUtils.isEmpty(email)) {
            Log.v(TAG, "empty email");
            return;
        }

        String password = "123456";

        // Create an account using Firebase auth
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If we reach here the auth was complete
                        if (task.isSuccessful()) {
                            // redirect user to main activity
                            Log.v(TAG, "User created");
                            Intent intent = new Intent(Signup.this, MainActivity.class);
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