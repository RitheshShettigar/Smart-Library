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

public class PricnipleActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricniple);
        drawerLayout=findViewById(R.id.drawer_layout);

        Button add_librarian=(Button)findViewById(R.id.add_librarian);
        Button user_report=(Button)findViewById(R.id.user_report);
        Button book_report=findViewById(R.id.book_report);
        Button library_details=findViewById(R.id.library_details);
        Button issue_report=findViewById(R.id.issue_report);

        issue_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PricnipleActivity.this,PrincipleBookIssueHistory.class);
                startActivity(intent);

            }
        });



        library_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PricnipleActivity.this,LibraryanDeatilsActivity.class);
                startActivity(intent);

            }
        });

        book_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PricnipleActivity.this,BookReportActivity.class);
                startActivity(intent);

            }
        });

        add_librarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PricnipleActivity.this,AddLibrarianActivity.class);
                startActivity(intent);
            }
        });


        user_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PricnipleActivity.this,UserReportActivity.class);
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
        Intent intent=new Intent(PricnipleActivity.this,PricnipleActivity.class);
        startActivity(intent);

    }
    public void ClickSetting(View view) {
        Intent intent=new Intent(PricnipleActivity.this,PrincipalPasswordupdateActivity.class);
        startActivity(intent);
    }

    public void ClickProfile(View view){
        Intent intent=new Intent(PricnipleActivity.this,PrincipleProfileActivity.class);
        startActivity(intent);
    }

    public void ClickAbout(View view){
        Intent intent=new Intent(PricnipleActivity.this,AboutActivity.class);
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

                Intent intent = new Intent(PricnipleActivity.this,LoginActivity.class);
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
