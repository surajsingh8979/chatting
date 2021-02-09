package com.example.chatting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chatting.Adapaters.ChatAdapter;
import com.example.chatting.Models.MessageModel;
import com.example.chatting.databinding.ActivityChatDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Date;

import static com.example.chatting.databinding.ActivityChatDetailsBinding.inflate;

public class ChatDetails extends AppCompatActivity {
        ActivityChatDetailsBinding binding;
        FirebaseDatabase database;
        FirebaseAuth auth;
    private Target profileimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
       final String senderId = auth.getUid();
        String reciverId=getIntent().getStringExtra("userId");
        String userName= getIntent().getStringExtra("userName");
        String profile= getIntent().getStringExtra("profile");

        binding.Usernamed.setText(userName);
        Picasso.get().load(profile).placeholder(R.drawable.pic).into(profileimg);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });

    final ArrayList<MessageModel>  messageModel = new ArrayList<>();

    final ChatAdapter chatAdapter = new ChatAdapter(messageModel, this ,reciverId);
     binding.Chatrecycler.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.Chatrecycler.setLayoutManager(layoutManager);

        final String senderRoom = senderId + reciverId;
        final String reciverRoom = reciverId + senderId;

        database.getReference().child("Chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModel.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    MessageModel model = snapshot1.getValue(MessageModel.class);
                    model.setMessageId(snapshot1.getKey());

                    messageModel.add(model);


                }

                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String message =  binding.type.getText().toString();
              final MessageModel model= new MessageModel(senderId, message);
                model.setTimestamp(new Date().getTime());
                binding.type.setText("");

                database.getReference().child("Chats").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       database.getReference().child("Chats").child(reciverRoom).push().setValue(model) .addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {

                           }
                       });
                    }
                });



            }
        });

    }
}