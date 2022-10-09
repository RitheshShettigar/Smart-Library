package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookReportOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BDSMyAdapter bdsMyAdapter;
    ArrayList<modelBdetails> list;
    DatabaseReference root= FirebaseDatabase.getInstance().getReference("Book");
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_report_order);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView=findViewById(R.id.bookliststudent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progress=findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        list= new ArrayList<>();
        bdsMyAdapter=new BDSMyAdapter(this,list);
        recyclerView.setAdapter(bdsMyAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    modelBdetails modelBdetails=dataSnapshot.getValue(modelBdetails.class);
                    list.add(modelBdetails);

                }
                bdsMyAdapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }





}