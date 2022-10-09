package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText password,username;
    Button btnlogin;
    Spinner spinner;
    ProgressBar progressbar2;
    FirebaseAuth auth;
    DatabaseReference mDatabase;
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password=(EditText) findViewById(R.id.password);
        username=(EditText) findViewById(R.id.username);
        btnlogin=(Button) findViewById(R.id.btnlogin);
        spinner=(Spinner) findViewById(R.id.spinner);
        progressbar2=(ProgressBar)findViewById(R.id.progressbar2);
        auth=FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.usertype,R.layout.support_simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
        progressbar2.setVisibility(View.GONE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spinner.getSelectedItem()==null||spinner.getSelectedItem()=="None"){
                    Toast.makeText(LoginActivity.this, "eee", Toast.LENGTH_SHORT).show();
                }
                // student login
                if (spinner.getSelectedItem().toString().equals("Student")){
                    if (username.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, "please enter user name", Toast.LENGTH_SHORT).show();
                        return;

                    } else if (password.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    mDatabase = FirebaseDatabase.getInstance().getReference("Register").child(username.getText().toString());
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String pass = snapshot.child("Password").getValue().toString();
                            String id=snapshot.child("Id").getValue().toString();
                            String name=snapshot.child("Username").getValue().toString();
                            String type=snapshot.child("type").getValue().toString();
                            String year=snapshot.child("year").getValue().toString();
                            String phone=snapshot.child("PhoneNumber").getValue().toString();
                            String email=snapshot.child("EmailId").getValue().toString();
                            String department=snapshot.child("deptC").getValue().toString();
                            String photo=snapshot.child("Image").getValue().toString();


                            if (password.getText().toString().equals(pass)) {
                                String s = snapshot.child("type").getValue().toString();
                                Toast.makeText(LoginActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                String spinnertxt = spinner.getSelectedItem().toString();
                                if (spinnertxt.equals(s)) {
                                    Toast.makeText(LoginActivity.this, "User login successful", Toast.LENGTH_SHORT).show();
                                    progressbar2.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(LoginActivity.this,UserActivity.class);
                                     intent1 = new Intent(LoginActivity.this, UserProfile.class);

                                     intent.putExtra("id",id);
                                    intent.putExtra("name",name);
                                    intent.putExtra("type",type);
                                    intent.putExtra("year",year);
                                    intent.putExtra("PhoneNumber",phone);
                                    intent.putExtra("department",department);
                                    intent.putExtra("EmailId",email);
                                    intent.putExtra("Image",photo);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(LoginActivity.this, "please enter correct email Id and password", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, "please enter correct email Id and password", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                //end student login
                else
                    // principal login
                    if (spinner.getSelectedItem().toString().equals("Principal")){
                        if (username.getText().toString().isEmpty()) {
                            Toast.makeText(LoginActivity.this, "please enter user name", Toast.LENGTH_SHORT).show();
                            return;

                        } else if (password.getText().toString().isEmpty()) {
                            Toast.makeText(LoginActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
                            return;

                        }

                        mDatabase = FirebaseDatabase.getInstance().getReference("Princi").child(username.getText().toString());
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String pass = snapshot.child("Password").getValue().toString();

                                if (password.getText().toString().equals(pass)) {
                                    String s = snapshot.child("type").getValue().toString();

                                    Toast.makeText(LoginActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                    String spinnertxt = spinner.getSelectedItem().toString();
                                    if (spinnertxt.equals(s)) {
                                        Toast.makeText(LoginActivity.this, "Principal login successful", Toast.LENGTH_SHORT).show();
                                        progressbar2.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(LoginActivity.this, PricnipleActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    //pri login end
                    else
                        // Faculties login
                        if (spinner.getSelectedItem().toString().equals("Faculties")){
                            if (username.getText().toString().isEmpty()) {
                                Toast.makeText(LoginActivity.this, "please enter user name", Toast.LENGTH_SHORT).show();
                                return;

                            } else if (password.getText().toString().isEmpty()) {
                                Toast.makeText(LoginActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
                                return;

                            }
                            mDatabase = FirebaseDatabase.getInstance().getReference("Register").child(username.getText().toString());
                            mDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String pass = snapshot.child("Password").getValue().toString();
                                    String id=snapshot.child("Id").getValue().toString();
                                    String name=snapshot.child("Username").getValue().toString();
                                    String type=snapshot.child("type").getValue().toString();
                                    String year=snapshot.child("year").getValue().toString();
                                    String phone=snapshot.child("PhoneNumber").getValue().toString();
                                    String email=snapshot.child("EmailId").getValue().toString();
                                    String department=snapshot.child("deptC").getValue().toString();
                                    String photo=snapshot.child("Image").getValue().toString();


                                    if (password.getText().toString().equals(pass)) {
                                        String s = snapshot.child("type").getValue().toString();

                                        Toast.makeText(LoginActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                        String spinnertxt = spinner.getSelectedItem().toString();
                                        if (spinnertxt.equals(s)) {
                                            Toast.makeText(LoginActivity.this, "Faculties login successful", Toast.LENGTH_SHORT).show();
                                            progressbar2.setVisibility(View.VISIBLE);
                                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                            intent1 = new Intent(LoginActivity.this, UserProfile.class);

                                            intent.putExtra("id",id);
                                            intent.putExtra("name",name);
                                            intent.putExtra("type",type);
                                            intent.putExtra("year",year);
                                            intent.putExtra("PhoneNumber",phone);
                                            intent.putExtra("EmailId",email);
                                            intent.putExtra("department",department);
                                            intent.putExtra("Image",photo);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(LoginActivity.this, id, Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(LoginActivity.this, "please enter correct email Id and password", Toast.LENGTH_SHORT).show();
                                    return;


                                }
                            });
                        }
                        // Faculties login end

                //Librarian login

                     if (spinner.getSelectedItem().toString().equals("Librarian")) {
                         if (username.getText().toString().isEmpty()) {
                             Toast.makeText(LoginActivity.this, "please enter user name", Toast.LENGTH_SHORT).show();

                         } else if (password.getText().toString().isEmpty()) {
                             Toast.makeText(LoginActivity.this, "please enter password", Toast.LENGTH_SHORT).show();

                         }
                         else if (!username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                             mDatabase = FirebaseDatabase.getInstance().getReference("Librarian");
                             auth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()) {
                                         Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                         startActivity(intent);
                                         Toast.makeText(LoginActivity.this, "Librarian Login Successful", Toast.LENGTH_SHORT).show();
                                         progressbar2.setVisibility(View.VISIBLE);

                                     } else {
                                         Toast.makeText(LoginActivity.this, "please enter correct email Id and password", Toast.LENGTH_SHORT).show();
                                     }

                                 }
                             });
                         }
                     }
                     //Librarian login
            }
        });
    }
}