package com.example.chatting.Adapaters;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatting.Fragments.Callsfragment;
import com.example.chatting.Fragments.Chatfragment;
import com.example.chatting.Fragments.Statusfragment;

public class FragmentsAdapter  extends FragmentPagerAdapter {

    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Chatfragment();
            case 1: return new Statusfragment();
            case 2: return new Callsfragment();
            default:return  new Chatfragment();
        }



    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title= null;
        if(position==0){
            title="CHATS";
        }
        if(position==1){
            title="STATUS";
        }
        if(position==2){
            title="CALLS";
        }


        return super.getPageTitle(position);
    }
}
