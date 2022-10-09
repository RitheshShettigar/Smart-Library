package com.example.smartlibrary;

import android.net.Uri;
import android.widget.EditText;

public class Faculties {
    String name,email,password,profile_image,phonenumber,id;

    public Faculties(String name, String email, String password, String profile_image, String phonenumber, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile_image = profile_image;
        this.phonenumber = phonenumber;
        this.id = id;
    }

    public Faculties(EditText name, EditText email, EditText password, EditText phoneNumber, Uri uri) {

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getId() {
        return id;
    }
}



