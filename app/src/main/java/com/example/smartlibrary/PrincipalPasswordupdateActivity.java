package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PrincipalPasswordupdateActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_passwordupdate);
        drawerLayout=findViewById(R.id.drawer_layout);

        EditText olddpass2=(EditText) findViewById(R.id.olddpass1);
        EditText newpass2=(EditText) findViewById(R.id.newpass1);
        EditText confirmpassword2=(EditText) findViewById(R.id.confirmpassword1);
        Button updatepassword2=(Button) findViewById(R.id.updatepassword1);

        updatepassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpassword3=olddpass2.getText().toString().trim();
                String newpassword3=newpass2.getText().toString().trim();
                String confirmpassword3=confirmpassword2.getText().toString().trim();




                reference= FirebaseDatabase.getInstance().getReference("Princi").child("pri");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String name=snapshot.child("Password").getValue().toString();


                        if(TextUtils.isEmpty(oldpassword3)){
                            Toast.makeText(PrincipalPasswordupdateActivity.this, "Enter your Current Password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else   if(TextUtils.isEmpty(newpassword3)){
                            Toast.makeText(PrincipalPasswordupdateActivity.this, "Enter your new Password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else  if(newpassword3.length()<6){
                            Toast.makeText(PrincipalPasswordupdateActivity.this, "password length must 6 characters...", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else  if(!confirmpassword3.equals(newpassword3)){
                            Toast.makeText(PrincipalPasswordupdateActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!name.equals(oldpassword3)){
                            Toast.makeText(PrincipalPasswordupdateActivity.this,"Password update successful",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if (!newpassword3.equals(confirmpassword3)){
                            Toast.makeText(PrincipalPasswordupdateActivity.this,"password do not math",Toast.LENGTH_SHORT).show();

                        }else {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("Password",newpassword3);
                            reference.updateChildren(map);

                            Intent intent1=new Intent(PrincipalPasswordupdateActivity.this,PricnipleActivity.class);
                            startActivity(intent1);
                            Toast.makeText(PrincipalPasswordupdateActivity.this,"password update successful",Toast.LENGTH_SHORT).show();

                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });




    }
    public void ClickMenu(View view) {
        //open drawer
        AdminActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        //close drawer
        AdminActivity.closeDrawer((drawerLayout));
    }

    public void ClickHome(View view){
        Intent intent=new Intent(PrincipalPasswordupdateActivity.this,PricnipleActivity.class);
        startActivity(intent);
    }
    public void ClickAbout(View view){
        Intent intent=new Intent(PrincipalPasswordupdateActivity.this,AboutActivity.class);
        startActivity(intent);

    }
    public void ClickProfile(View view){
        Intent intent=new Intent(PrincipalPasswordupdateActivity.this,PrincipleProfileActivity.class);
        startActivity(intent);
    }
    public void ClickSetting(View view){
        Intent intent=new Intent(PrincipalPasswordupdateActivity.this,AdminResetPasswordAactivity.class);
        startActivity(intent);
    }
    public void ClickLogout(View view){
        AdminActivity.closeDrawer(drawerLayout);
    }


    @Override
    protected void onPause() {
        super.onPause();
        AdminActivity.closeDrawer(drawerLayout);
    }
}