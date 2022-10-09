package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class BookOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText copies;
    EditText dob;
    private int mYear,mMonth,mDay;
    TextView id,name;
    Button orderbtn;






    DatabaseReference mDatabase;
    static  final String Order="order";
    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_order);

        dob= findViewById(R.id.dob);
        copies=  findViewById(R.id.copies);
        id=findViewById(R.id.id);
        name=findViewById(R.id.name);
        orderbtn=findViewById(R.id.orderbtn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Intent intent=getIntent();
//book
        String bname,bid;
        bid=intent.getStringExtra("id3");
       bname=intent.getStringExtra("name3");
       name.setText(bname);
        id.setText(bid);
        //Log.d(bname,"name12");


        copies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c=Calendar.getInstance();
                mYear=c.get(Calendar.YEAR);
                mMonth=c.get(Calendar.MONTH);
                mDay=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(BookOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        copies.setText(dayOfMonth+"-"+(month+1)+"-"+year);

                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();

            }
        });






       dob.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final Calendar c=Calendar.getInstance();
               mYear=c.get(Calendar.YEAR);
               mMonth=c.get(Calendar.MONTH);
               mDay=c.get(Calendar.DAY_OF_MONTH);
               DatePickerDialog datePickerDialog=new DatePickerDialog(BookOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                       dob.setText(dayOfMonth+"-"+(month+1)+"-"+year);

                   }
               },mYear,mMonth,mDay);
               datePickerDialog.show();
           }
       });

       //order button click
        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadfile();
            }
        });

    }

    //file upload to firebase
    private void uploadfile() {

        String username1,userid1,usertype1,dept4;
        Intent intent=getIntent();

        //student

        username1 =intent.getStringExtra("name4");
        userid1=intent.getStringExtra("id4");
        usertype1=intent.getStringExtra("type4");
        dept4=intent.getStringExtra("dept4");



        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        //  String i = firebaseUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("Order").child(userid1);

        HashMap<String, String> hashMap = new HashMap<>();

        // hashMap.put("id", i);
        hashMap.put("BookId",id.getText().toString());
        hashMap.put("BookName",name.getText().toString());
        hashMap.put("Copies",copies.getText().toString());
        hashMap.put("returnDate",dob.getText().toString());
        hashMap.put("UserName",username1.toString());
        hashMap.put("UserId",userid1.toString());
        hashMap.put("dept",dept4.toString());
        hashMap.put("UserType",usertype1.toString());
        mDatabase.setValue(hashMap);

        mDatabase = FirebaseDatabase.getInstance().getReference("Order1").child(userid1);
        hashMap.put("BookName",name.getText().toString());
        hashMap.put("Copies",copies.getText().toString());
        hashMap.put("returnDate",dob.getText().toString());
        hashMap.put("UserName",username1.toString());
        hashMap.put("UserId",userid1.toString());
        hashMap.put("dept",dept4.toString());
        hashMap.put("UserType",usertype1.toString());
        mDatabase.setValue(hashMap);


        Toast.makeText(this, "Order Successful", Toast.LENGTH_SHORT).show();
        copies.setText("");
        dob.setText("");

    }





    private void updateLabel() {
        String myFormate="mm/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormate, Locale.US);
      //  days.setText(dateFormat.format(myCalender.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice=adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}