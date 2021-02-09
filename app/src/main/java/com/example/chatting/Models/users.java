package com.example.chatting.Models;

import android.net.Uri;

public class users {

     static String status;
    String userName;
    String profile;
    String mail;
    String password;
    static String userId;
    String lastMessage;


    public users(String profile, String userName, String mail, String password, String useId, String lastMessage){
        this.profile = profile;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userId = useId;
        this.lastMessage = lastMessage;

    }

    public users(){


    }

    //
    public users( String userName, String mail, String password )
    {

        this.userName = userName;
        this.mail = mail;
        this.password = password;

    }

   

    public static String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static Uri getprofile() {
        return null;
    }

    public static CharSequence getuserName() {
        return null;
    }


    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setUseId(String uid) {

    }
}
