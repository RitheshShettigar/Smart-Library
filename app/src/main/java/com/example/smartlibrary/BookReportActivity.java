package com.example.smartlibrary;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.grpc.Context;

public class BookReportActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BDMyAdapter bdMyAdapter;
    ArrayList<modelBdetails> list;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Book");
   SearchView searchView;
    ProgressBar progress;
   EditText search;
    ImageButton sarchhide;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*  sarchhide =findViewById(R.id.searchhide);
        sarchhide.setOnClickListener(this::onClick);
        search =findViewById(R.id.search_bar);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

        //updateToken(FirebaseMessaging.getInstance().getToken().toString());

       // return recyclerView;


        recyclerView = findViewById(R.id.booklist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);


        list = new ArrayList<>();
        bdMyAdapter = new BDMyAdapter(this, list);
        recyclerView.setAdapter(bdMyAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    modelBdetails modelBdetails = dataSnapshot.getValue(modelBdetails.class);
                    list.add(modelBdetails);

                }
                bdMyAdapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
   /* private void onClick(View view) {

        if (i == 0) {
            search.setVisibility(View.VISIBLE);
            sarchhide.setImageResource(R.drawable.cancealuser);

            search.requestFocus();

            i++;
        } else if (i == 1) {
            search.setVisibility(View.GONE);
            sarchhide.setImageResource(R.drawable.searchuser);

            i = 0;
        }
    }]*/


    //func
//search bar
    private void searchUsers(String s) {

        final FirebaseUser fuser= FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Book").orderByChild("BookId")
                .startAt(s)
                .endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    modelBdetails modelBdetails = snapshot.getValue(modelBdetails.class);
                    assert modelBdetails != null;
                    assert fuser != null;
                    if (!modelBdetails.getBookId().equals(fuser.getUid())) {
                        list.add(modelBdetails);
                    }
                }

                bdMyAdapter = new BDMyAdapter(getContext(),list, false);
                recyclerView.setAdapter(bdMyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }






    //search code
/*    @Override
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

        FirebaseRecyclerOptions<modelBdetails>options=
                new FirebaseRecyclerOptions.Builder<modelBdetails>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Book").orderByChild("BookName").startAt(s).endAt(s+"\ufaff"),modelBdetails.class)
                .build();

        bdMyAdapter=new BDMyAdapter(options);
        bdMyAdapter.startListening();
        recyclerView.setAdapter(bdMyAdapter);
    }*/
}