package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrincipleBookIssueHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    PBIHAdapter pbihAdapter;
    ArrayList<modelPBIdetails> list;
    DatabaseReference root= FirebaseDatabase.getInstance().getReference("Order1");
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principle_book_issue_history);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView=findViewById(R.id.principleiss);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progress=findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        list= new ArrayList<>();
        pbihAdapter=new PBIHAdapter(this,list);
        recyclerView.setAdapter(pbihAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    modelPBIdetails modelPBIdetails=dataSnapshot.getValue(modelPBIdetails.class);
                    list.add(modelPBIdetails);

                }
                pbihAdapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}