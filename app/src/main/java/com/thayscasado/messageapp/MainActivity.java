package com.thayscasado.messageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // Reference to firebase database
    FirebaseDatabase mfirebaseIntance;
    DatabaseReference mfirebaseDatabase;

    // user auth to get the user sender email
    FirebaseAuth auth;

    private EditText emailReceiver;
    private Button btnStartChat;
    private String emailSender;
    private String[] chatID = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        emailSender = auth.getCurrentUser().getEmail();
        emailReceiver = findViewById(R.id.editTextReceiver);
        btnStartChat = findViewById(R.id.buttonStart);

        btnStartChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // remove @ from sender and receiver
                String[] sender = emailSender.split("@");
                String[] receiver = emailReceiver.getText().toString().trim().split("@");
                chatID[0] = sender[0] + receiver[0];
                chatID[1] = receiver[0] + sender[0];
                startChat();
            }
        });
    }

    private void startChat() {

        // Write a message to the database
        mfirebaseIntance = FirebaseDatabase.getInstance();
        mfirebaseDatabase = mfirebaseIntance.getReference("chats");
        mfirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean doesChatExist = false;
                // check if the emailReceiver exist in users table
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (child.getKey().equals(chatID[0]) || child.getKey().equals(chatID[1])) {
                        Intent intent = new Intent(MainActivity.this, ChatboxActivity.class);
                        intent.putExtra("chatID", chatID);
                        startActivity(intent);
                        finish();
                        doesChatExist = true;
                    }

                }
                if (!doesChatExist) {
                    // create one chatbox
                    // create a random key
                    String chatboxId = mfirebaseDatabase.push().getKey();
                    mfirebaseDatabase.child(chatID[0]).setValue("");
                    Intent intent = new Intent(MainActivity.this, ChatboxActivity.class);
                    intent.putExtra("chatID", chatID);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}