package com.example.chatting.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewbinding.ViewBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.chatting.Adapaters.usersAdapater;
import com.example.chatting.Models.users;
import com.example.chatting.R;
import com.example.chatting.databinding.FragmentChatfragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Chatfragment extends Fragment {


   public Chatfragment() {
       // Required empty public constructor

   }
    FragmentChatfragmentBinding binding;
    ArrayList<users> list = new ArrayList<>();
    FirebaseDatabase database;


        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatfragmentBinding.inflate(inflater, container, false);

        database= FirebaseDatabase.getInstance();
            usersAdapater adapater = new usersAdapater( list, getContext());
        binding.Chatrecycler.setAdapter(adapater);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.Chatrecycler.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    users Users = dataSnapshot.getValue(users.class);

                    if (!users.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                    list.add(Users);



                }
                adapater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }


             });
        return binding.getRoot();
        }
}