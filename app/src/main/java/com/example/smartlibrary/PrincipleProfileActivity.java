package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PrincipleProfileActivity extends AppCompatActivity {

    EditText name3,email3,phone3;
    Button update3;


    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principle_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        name3=findViewById(R.id.name9);
        email3=findViewById(R.id.email9);
        phone3=findViewById(R.id.phone9);
        update3=findViewById(R.id.update9);

        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

       mDatabase = FirebaseDatabase.getInstance().getReference("pri").child("pri2");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                String email=snapshot.child("email").getValue().toString();
                String phone=snapshot.child("phone").getValue().toString();

                name3.setText(name);
                email3.setText(email);
                phone3.setText(phone);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        update3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                updatefire();

            }
        });



    }

    private void updatefire() {

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String id = firebaseUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("pri").child("pri2");

        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name3.getText().toString());
        map.put("email",email3.getText().toString());
        map.put("phone",phone3.getText().toString());
        mDatabase.setValue(map);
        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();






    }



}