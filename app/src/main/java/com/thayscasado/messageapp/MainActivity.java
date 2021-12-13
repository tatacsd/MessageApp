package com.thayscasado.messageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // Reference to firebase database
    FirebaseDatabase mfirebaseIntance;
    DatabaseReference mfirebaseDatabase;

    private EditText emailSender, emailReceiver;
    private Button btnStartChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Write a message to the database
        mfirebaseIntance = FirebaseDatabase.getInstance();
        mfirebaseDatabase = mfirebaseIntance.getReference("chats");


    }
}