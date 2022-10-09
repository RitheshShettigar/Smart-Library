package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationActivity extends AppCompatActivity {

    TextView notification;
    DatabaseReference database;

    String userid;
    String name1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notification=findViewById(R.id.notification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent intent=getIntent();
        name1=intent.getStringExtra("id1");


        database= FirebaseDatabase.getInstance().getReference().child("Approved");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.child(name1).exists()){
                    notification.setText("Your Book is Ready Please collect your book in the college library");
                   // Toast.makeText(NotificationActivity.this, name1, Toast.LENGTH_SHORT).show();
              }else {
                    notification.setText("");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }
}