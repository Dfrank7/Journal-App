package com.example.dfrank.journalapp.model;

import android.net.Uri;

public class Profile {

    public String name;
    public String email;
    private Uri photoUrl;

    public Profile(){}
    public Profile(String name, String email){
        this.name = name;
        this.email = email;
    }
    public Profile(String name, String email, Uri photoUrl){
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
    }
}
