package com.example.smartlibrary;

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

public class AdminActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        drawerLayout=findViewById(R.id.drawer_layout);

        Button addbook = (Button) findViewById(R.id.addbook);
        Button bookrequested=(Button)findViewById(R.id.bookrequested);
        Button bookreport=(Button)findViewById(R.id.bookreport);
        Button adduser=(Button)findViewById(R.id.adduser);
        Button user_report=(Button)findViewById(R.id.user_report);






        bookrequested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,BookRequestedAdminActivity.class);
                startActivity(intent);

            }
        });

        user_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,UserReportActivity.class);
                startActivity(intent);


            }
        });

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,AddUserActivity.class);
                startActivity(intent);


            }
        });



        bookreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,BookReportActivity.class);
                startActivity(intent);

            }
        });








        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivity.this,AddBookActivity.class);
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

    public void ClickAbout(View view){
        Intent intent=new Intent(AdminActivity.this,AboutActivity.class);
        startActivity(intent);

    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
   /* public void ClickHome(View view){
        recreate();
    }*/


    public void ClickSetting(View view) {
        Intent intent=new Intent(AdminActivity.this,AdminResetPasswordAactivity.class);
        startActivity(intent);

    }
    public void ClickHome(View view){
        Intent intent=new Intent(AdminActivity.this,AdminActivity.class);
        startActivity(intent);
    }

    public void ClickProfile(View view){

        Intent intent=new Intent(AdminActivity.this,LibraryanDeatilsActivity.class);
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

                Intent intent = new Intent(AdminActivity.this,LoginActivity.class);
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