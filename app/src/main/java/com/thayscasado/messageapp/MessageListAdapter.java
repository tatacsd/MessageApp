package com.thayscasado.messageapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>{

    // Member variables
    Context mContext;
    ArrayList<Message> messageArrayList;

    // Constructor
    public MessageListAdapter(Context mContext, ArrayList<Message> messageArrayList) {
        this.mContext = mContext;
        this.messageArrayList = messageArrayList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdapter.MessageViewHolder holder, int position) {
        Message message = messageArrayList.get(position);
        holder.username.setText(message.getUsername());
        holder.message.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        // return the list length
        return messageArrayList.size();
    }

    public class Message{
        String username;
        String message;

        public Message(String username, String message) {
            this.username = username;
            this.message = message;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    // Class HOLDER
    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        // Member variables
        TextView username;
        TextView message;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            // get the ui reference
            username = itemView.findViewById(R.id.tvUserName);
            message = itemView.findViewById(R.id.tvMessage);

        }
    }
}
