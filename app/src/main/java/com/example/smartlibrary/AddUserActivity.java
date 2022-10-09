package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Random;

public class AddUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText id,name,email,password,confirm_password,phoneNumber,department;
    private Spinner type,year;
    private Button btn_adduser;
    private ImageView profile_image;
    private static final int SELECT_PHOTO=100;
    Uri imageUri;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    static  final String USER="user";
    private static final String TAG="AddStudentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       id=findViewById(R.id.id2);
        name=findViewById(R.id.name2);
        department=findViewById(R.id.dept2);
        type= findViewById(R.id.type2);
        year=  findViewById(R.id.year2);
        email=findViewById(R.id.email2);
        phoneNumber=findViewById(R.id.phoneNumber2);
        password= findViewById(R.id.password2);
        confirm_password=findViewById(R.id.confirm_password2);
        btn_adduser= findViewById(R.id.btn_adduser);
        profile_image= findViewById(R.id.profile_image1);

        database=FirebaseDatabase.getInstance();
      //  mDatabase=database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();

        //spinner
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.type,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(AddUserActivity.this);

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.year,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter1);
        year.setOnItemSelectedListener(AddUserActivity.this);

        //end spinner

        btn_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id3 = id.getText().toString();
                String type3 = type.getSelectedItem().toString().trim();
                String name3 = name.getText().toString().trim();
                String year3=year.getSelectedItem().toString().trim();
                String phone3 = phoneNumber.getText().toString().trim();
                String department3 = department.getText().toString().trim();
                String email3=email.getText().toString().trim();
                String password3 = password.getText().toString().trim();
                String conpassword3 =confirm_password.getText().toString().trim();
                String profile_image =imageUri.toString().trim();

                if(TextUtils.isEmpty(id3)) {
                    Toast.makeText(getApplicationContext(), "Enter Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(type3)) {
                    Toast.makeText(getApplicationContext(), "Enter Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name3)) {
                    Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(year3)) {
                    Toast.makeText(getApplicationContext(), "Enter Year", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(department3)) {
                    Toast.makeText(getApplicationContext(), "Enter class/department", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(phone3)){
                    Toast.makeText(getApplicationContext(), "Enter phone", Toast.LENGTH_SHORT).show();
                    return;

                }
               if(phone3.length()<10){
                   Toast.makeText(getApplicationContext(), "Phone Number must be 10 digit", Toast.LENGTH_SHORT).show();
                   return;

               }

                if(TextUtils.isEmpty(email3)) {
                    Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password3) ){
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(conpassword3)) {
                    Toast.makeText(getApplicationContext(), "Enter ConfirmPassword", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!conpassword3.equals(password3)) {
                    confirm_password.setError("Password do not match");
                    return;
                }
                if(TextUtils.isEmpty(profile_image)){
                    Toast.makeText(getApplicationContext(), "Enter profile", Toast.LENGTH_SHORT).show();
                    return;

                } if (imageUri!=null){

                    uploadImage();
                } else {
                    Toast.makeText(AddUserActivity.this, "please add photo", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });



         //image add to phone
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AddUserActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent, 100);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.cancelPermissionRequest();
                            }
                        }).check();
            }
        });
        //end image add to phone


    }
    //upload image and other deatils to firebase
    private void uploadImage() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File Upload....");
        dialog.show();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference("Image1" + new Random().nextInt(60));
        uploader.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                User user = new User(
                                        id,
                                        type,
                                        name,
                                        year,
                                        email,
                                        password,
                                        phoneNumber,
                                        department,
                                        uri
                                );
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                String userid = firebaseUser.getUid();
                                mDatabase = FirebaseDatabase.getInstance().getReference("Register").child(id.getText().toString());
                                HashMap<String, String> hashMap = new HashMap<>();

                               // hashMap.put("id", userid);
                                hashMap.put("Id",id.getText().toString());
                                hashMap.put("type",type.getSelectedItem().toString());
                                hashMap.put("Username",name.getText().toString());
                                hashMap.put("year",year.getSelectedItem().toString());
                                hashMap.put("PhoneNumber",phoneNumber.getText().toString());
                                hashMap.put("deptC",department.getText().toString());
                                hashMap.put("EmailId", email.getText().toString());
                                hashMap.put("Password", password.getText().toString());
                                hashMap.put("Image",uri.toString());



                                mDatabase.setValue(hashMap);
                                id.setText("");
                                type.setAdapter(type.getAdapter());
                                name.setText("");
                                department.setText("");
                                year.setAdapter(year.getAdapter());
                                email.setText("");
                                password.setText("");
                                phoneNumber.setText("");
                                confirm_password.setText("");
                                profile_image.setImageResource(R.drawable.imageperson);





                            }
                        });
                        dialog.dismiss();
                        Toast.makeText(AddUserActivity.this, "Fill uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                dialog.setMessage("uploaded:" + (int) percent + "%");
            }
        });


    }
    //end upload image and other deatils to firebase


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice=adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {

            imageUri = data.getData();
            profile_image.setImageURI(imageUri);

        }
    }

    //end image

}