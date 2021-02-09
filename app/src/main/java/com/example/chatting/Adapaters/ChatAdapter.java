package com.example.chatting.Adapaters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatting.Models.MessageModel;
import com.example.chatting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;
    int SENDER_VIEW_TYPE =1;
    int RECIVER_VIEW_TYPE = 2;
    private Object reciverviewholder;
    private Object senderviewholder;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE) {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new senderviewholder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_reciver, parent, false);
            return new reciverviewholder(view);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else{
            return RECIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    MessageModel messageModel= messageModels.get(position);
    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            new AlertDialog.Builder(context).setTitle("Delete").setMessage("Are you sure you want to delete this message")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase database= FirebaseDatabase.getInstance();
                            String sender= FirebaseAuth.getInstance().getUid() + recId;
                            database.getReference().child("chats").child(sender).child(messageModel.getMessageId())
                                    .setValue(null);

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                }
            }).show();



            return false;
        }
    });


    if(holder.getClass()==senderviewholder.class){
        ((senderviewholder)holder).sendermsg.setText(messageModel.getMessage());
    }
    else{
        ((reciverviewholder)holder).reciverMsg.setText(messageModel.getMessage());

    }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class reciverviewholder extends RecyclerView.ViewHolder {

        TextView reciverMsg, recivertime;

        public reciverviewholder(@NonNull View itemView) {
            super(itemView);
            reciverMsg = itemView.findViewById(R.id.recivertext);
            recivertime = itemView.findViewById(R.id.recivertime);


        }
    }
    public class senderviewholder extends RecyclerView.ViewHolder {
        TextView sendermsg, sendertime;

        public senderviewholder(@NonNull View itemView) {
            super(itemView);

            sendermsg= itemView.findViewById(R.id.sendertext);
            sendertime= itemView.findViewById(R.id.sendertime);
        }
    }

}
