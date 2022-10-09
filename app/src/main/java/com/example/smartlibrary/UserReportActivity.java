package com.example.smartlibrary;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserReportActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    SDMyAdapter sdMyAdapter;
    modelSdetails modelsdetails;
    ArrayList<modelSdetails> list;
    ProgressBar progress;
    SearchView search1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.userlist);
        database= FirebaseDatabase.getInstance().getReference().child("Register");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progress=findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        search1=findViewById(R.id.search1);

        list=new ArrayList<>();
        sdMyAdapter=new SDMyAdapter(this,list);
        recyclerView.setAdapter(sdMyAdapter);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    modelSdetails modelSdetails=dataSnapshot.getValue(modelSdetails.class);
                    list.add(modelSdetails);
                }
                sdMyAdapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);

        MenuItem menuItem=menu.findItem(R.id.search1);


        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type her to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



    private void processsearch(String s) {
        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Register").orderByChild("Id")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    modelSdetails modelsdetails = snapshot.getValue(modelSdetails.class);

                    assert modelsdetails != null;
                    assert fuser != null;
                    if (!modelsdetails.getId().equals(fuser.getUid())){
                        list.add(modelsdetails);
                    }
                }

                sdMyAdapter = new SDMyAdapter(getContext(), list,false);
                recyclerView.setAdapter(sdMyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/
}