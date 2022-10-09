package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookRequestedAdminActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BRAMyAdapter braMyAdapter;
    ArrayList<modelBRdetails> list;
    DatabaseReference root= FirebaseDatabase.getInstance().getReference("Order");
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_requested_admin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView=findViewById(R.id.bookrequested);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progress=findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);


        list= new ArrayList<>();
        braMyAdapter=new BRAMyAdapter(this,list);
        recyclerView.setAdapter(braMyAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    modelBRdetails modelBRdetails=dataSnapshot.getValue(modelBRdetails.class);
                    list.add(modelBRdetails);

                }
                braMyAdapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}