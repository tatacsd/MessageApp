package com.thayscasado.messageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatboxActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MessageListAdapter adapter;
    ArrayList<MessageListAdapter.Message> messageArrayList;

    // DATABASE VARIABLES
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userName;
    private String Message;
    private String[] chatID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox);

        chatID = getIntent().getStringArrayExtra("chatID");

        // Reference to firebase database
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("chats");

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Checking database
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (child.getKey().equals(chatID[0]) || child.getKey().equals(chatID[1])) {
                        // Get all messages and add them to the arrayList
                        mFirebaseDatabase.child(child.getKey()).child("message");

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}