package com.example.smartlibrary;

import static com.example.smartlibrary.AdminActivity.openDrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Intent intent2;
    String name,id,type,dept;
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        drawerLayout=findViewById(R.id.drawer_layout);
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        id=intent.getStringExtra("id");
        type=intent.getStringExtra("type");
       dept=intent.getStringExtra("department");

        Button book_report=findViewById(R.id.book_report);
        Button issues_book_history=findViewById(R.id.issues_book_history);

        ImageView notification1=findViewById(R.id.notification1);

        database= FirebaseDatabase.getInstance().getReference().child("Approved");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // String id=snapshot.child("UserId").getValue().toString();

                if (snapshot.child(id).exists()){
                    notification1.setImageResource(R.drawable.notificationred);

                }else {
                    notification1.setImageResource(R.drawable.notification);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        notification1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserActivity.this,NotificationActivity.class);
                intent.putExtra("id1",id);
                startActivity(intent);

            }
        });


        issues_book_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserActivity.this,IssuedBookHistoryUserActivity.class);
                intent.putExtra("id8",id);
              //  Toast.makeText(UserActivity.this, id, Toast.LENGTH_SHORT).show();
                startActivity(intent);


            }
        });




        book_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserActivity.this,BookReportOrderActivity.class);

                intent.putExtra("name1",name);
                intent.putExtra("id1",id);
                intent.putExtra("type1",type);
                intent.putExtra("department",dept);
                startActivity(intent);




            }
        });




    }
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer((GravityCompat.START));
    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view){
        Intent intent=new Intent(UserActivity.this,UserActivity.class);
        startActivity(intent);
    }
    public void ClickAbout(View view){
        Intent intent=new Intent(UserActivity.this,AboutActivity.class);
        startActivity(intent);

    }
    public void ClickSetting(View view) {

        String uid2;
        Intent intent5=getIntent();
        uid2=intent5.getStringExtra("id");

        Intent intent = new Intent(UserActivity.this,AdminResetPasswordAactivity.class);
        intent.putExtra("id66",uid2);
        startActivity(intent);






    }

    public void ClickProfile(View view){
        intent2=getIntent();
        name=intent2.getStringExtra("name");
        id=intent2.getStringExtra("id");
        String phone=intent2.getStringExtra("PhoneNumber");
        String email=intent2.getStringExtra("EmailId");
        dept=intent2.getStringExtra("department");
        String image=intent2.getStringExtra("Image");
       type=intent2.getStringExtra("type");

        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(UserActivity.this,UserProfile.class);
        intent.putExtra("name1",name);
        intent.putExtra("id1",id);
        intent.putExtra("phone1",phone);
        intent.putExtra("email1",email);
        intent.putExtra("year1",dept);
        intent.putExtra("type1",type);
        intent.putExtra("image1",image);

        startActivity(intent);
    }

    public void ClickLogout(View view) {
        //close app
        logout(this);
    }

    public void logout(final Activity activity) {


        //initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(UserActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        //initialize intent
        Intent intent = new Intent(activity, aClass);
        //set flog
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);


    }


}