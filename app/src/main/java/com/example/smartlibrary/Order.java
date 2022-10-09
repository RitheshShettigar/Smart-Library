package com.example.smartlibrary;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Order {
    TextView name;
    TextView id;
    EditText copies;
    Spinner dob;

    public Order(TextView name, TextView id, EditText copies, Spinner dob) {
        this.name = name;
        this.id = id;
        this.copies = copies;
        this.dob = dob;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getId() {
        return id;
    }

    public void setId(TextView id) {
        this.id = id;
    }

    public EditText getCopies() {
        return copies;
    }

    public void setCopies(EditText copies) {
        this.copies = copies;
    }

    public Spinner getDob() {
        return dob;
    }

    public void setDob(Spinner dob) {
        this.dob = dob;
    }
}

