package com.example.smartlibrary;

import android.net.Uri;
import android.widget.EditText;

public class Librarian {
   EditText name;
    EditText email;
    EditText phone;
    EditText password;
    Uri profileImage;

    public Librarian(EditText name, EditText email,EditText phone, EditText password, Uri profileImage) {
        this.name = name;
        this.phone=phone;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
    }

    public EditText getName() {
        return name;
    }

    public EditText getEmail() {
        return email;
    }

    public EditText getPassword() {
        return password;
    }

    public EditText getPhone() {
        return phone;
    }

    public Uri getProfileImage() {
        return profileImage;
    }
}
