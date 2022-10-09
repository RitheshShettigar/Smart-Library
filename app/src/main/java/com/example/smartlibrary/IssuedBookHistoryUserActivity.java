package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IssuedBookHistoryUserActivity extends AppCompatActivity {
    TextView bookid4,bookname4,bookreturn4,copies4;

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_book_history_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      bookid4=(TextView)findViewById(R.id.boid1);
        bookname4=(TextView)findViewById(R.id.boname1);
        bookreturn4=(TextView)findViewById(R.id.boreturndate1);
        copies4=(TextView)findViewById(R.id.bocopiese1);

          Intent intent=getIntent();
       String id9;
       id9=intent.getStringExtra("id8");

       /* intent.putExtra("id10",id9);
        startActivity(intent);*/

        DatabaseReference root=FirebaseDatabase.getInstance().getReference("Order").child(id9);
       root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookid4.setText(snapshot.child("BookId").getValue().toString());
                bookname4.setText(snapshot.child("BookName").getValue().toString());
                bookreturn4.setText(snapshot.child("returnDate").getValue().toString());
                copies4.setText(snapshot.child("Copies").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
}