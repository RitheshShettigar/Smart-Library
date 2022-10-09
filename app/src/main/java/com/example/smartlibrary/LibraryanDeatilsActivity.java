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

public class LibraryanDeatilsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LDMyAdapter ldMyAdapter;
    ArrayList<modelLdetails> list;
    DatabaseReference root= FirebaseDatabase.getInstance().getReference("Librarian");

    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraryan_deatils);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.liblist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progress=findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        list= new ArrayList<>();
       ldMyAdapter =new LDMyAdapter(this,list);
        recyclerView.setAdapter(ldMyAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    modelLdetails modelLdeatails=dataSnapshot.getValue(modelLdetails.class);
                    list.add(modelLdeatails);

                }
                ldMyAdapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}