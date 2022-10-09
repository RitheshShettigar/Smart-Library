package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminResetPasswordAactivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FirebaseUser firebaseAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reset_password_aactivity);
        drawerLayout = findViewById(R.id.drawer_layout);

        EditText olddpass=(EditText) findViewById(R.id.olddpass);
        EditText newpass=(EditText) findViewById(R.id.newpass);
        EditText confirmpassword=(EditText) findViewById(R.id.confirmpassword);
        Button updatepassword=(Button) findViewById(R.id.updatepassword);

      String uid9;
        Intent intent=getIntent();
       uid9=intent.getStringExtra("id66");
      //  Toast.makeText(this, uid9, Toast.LENGTH_SHORT).show();



        updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpassword=olddpass.getText().toString().trim();
                String newpassword=newpass.getText().toString().trim();
                String confirmpassword1=confirmpassword.getText().toString().trim();
                




                reference=FirebaseDatabase.getInstance().getReference("Register").child(uid9);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String name=snapshot.child("Password").getValue().toString();


                        if(TextUtils.isEmpty(oldpassword)){
                            Toast.makeText(AdminResetPasswordAactivity.this, "Enter your Current Password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                     else   if(TextUtils.isEmpty(newpassword)){
                            Toast.makeText(AdminResetPasswordAactivity.this, "Enter your new Password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else  if(newpassword.length()<6){
                            Toast.makeText(AdminResetPasswordAactivity.this, "password length must 6 characters...", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else  if(!confirmpassword1.equals(newpassword)){
                            Toast.makeText(AdminResetPasswordAactivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
                            return;
                        }

                           if (!name.equals(oldpassword)){
                                Toast.makeText(AdminResetPasswordAactivity.this,"Password update successful",Toast.LENGTH_SHORT).show();
                            return;
                        }
                           else if (!newpassword.equals(confirmpassword1)){
                                Toast.makeText(AdminResetPasswordAactivity.this,"password do not math",Toast.LENGTH_SHORT).show();

                            }else {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("Password",newpassword);
                                reference.updateChildren(map);

                         Intent intent1=new Intent(AdminResetPasswordAactivity.this,UserActivity.class);
                              startActivity(intent1);
                            Toast.makeText(AdminResetPasswordAactivity.this,"password update successful",Toast.LENGTH_SHORT).show();

                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }



    private FirebaseUser firebaseAuth() {
        return null;
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
        Intent intent=new Intent(AdminResetPasswordAactivity.this,UserActivity.class);
        startActivity(intent);
    }
    public void ClickProfile(View view){
        Intent intent=new Intent(AdminResetPasswordAactivity.this,UserProfile.class);
        startActivity(intent);
    }
    public void ClickAbout(View view){
        Intent intent=new Intent(AdminResetPasswordAactivity.this,AboutActivity.class);
        startActivity(intent);

    }
    public void ClickSetting(View view){
        Intent intent=new Intent(AdminResetPasswordAactivity.this,AdminResetPasswordAactivity.class);
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