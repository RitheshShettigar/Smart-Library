package com.example.smartlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    Intent intent;
    TextView name,id,year,number,email,type;
    ImageView profile2;

    String name1,phone1,email1,profile1,type1;
    String id1;
    String year1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        name=findViewById(R.id.name);
        id=findViewById(R.id.id);
        year=findViewById(R.id.year);
        email=findViewById(R.id.libemail);
        number=findViewById(R.id.libnumber);
        type=findViewById(R.id.type);
       // profile2=findViewById(R.id.profile);


      Intent  intent=getIntent();

      name1 =intent.getStringExtra("name1");
      id1=intent.getStringExtra("id1");
      year1=intent.getStringExtra("year1");
      phone1 =intent.getStringExtra("phone1");
      email1=intent.getStringExtra("email1");
      type1=intent.getStringExtra("type1");

        profile1=intent.getStringExtra("image1");
        name.setText(name1);
        type.setText(type1);
        id.setText(id1);
        year.setText(year1);
        number.setText(phone1);
        email.setText(email1);
      // profile2.setImageURI(Uri.parse(profile1));

    }
}