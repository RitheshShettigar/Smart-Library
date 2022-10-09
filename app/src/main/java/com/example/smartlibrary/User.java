package com.example.smartlibrary;

import android.net.Uri;
import android.widget.EditText;
import android.widget.Spinner;

import java.security.PublicKey;

public class User {
    public EditText id;
    public Spinner type;
    public EditText name;
    public Spinner year;
    public EditText email;
    public EditText password;
    public EditText phoneNumber;
    public EditText department;

    public Uri profile_image1;

    public User(EditText id, Spinner type, EditText name, Spinner year, EditText email,EditText department, EditText password, EditText phoneNumber, Uri profile_image1) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.department = department;
        this.year = year;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profile_image1 = profile_image1;
    }

    public EditText getId() {
        return id;
    }

    public void setId(EditText id) {
        this.id = id;
    }

    public Spinner getType() {
        return type;
    }

    public void setType(Spinner type) {
        this.type = type;
    }

    public EditText getName() {
        return name;
    }

    public void setName(EditText name) {
        this.name = name;
    }

    public EditText getDepartment() {
        return department;
    }

    public void setDepartment(EditText department) {
        this.department = department;
    }

    public Spinner getYear() {
        return year;
    }

    public void setYear(Spinner year) {
        this.year = year;
    }

    public EditText getEmail() {
        return email;
    }

    public void setEmail(EditText email) {
        this.email = email;
    }

    public EditText getPassword() {
        return password;
    }

    public void setPassword(EditText password) {
        this.password = password;
    }

    public EditText getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(EditText phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Uri getProfile_image1() {
        return profile_image1;
    }

    public void setProfile_image1(Uri profile_image1) {
        this.profile_image1 = profile_image1;
    }
}
