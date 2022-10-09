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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

public class AddLibrarianActivity extends AppCompatActivity {
    ImageView profileImage;
    EditText name, email, password, confirm_passwordEditText1,phone;
    Button btn_addlibrarian1;
    private static final int SELECT_PHOTO = 100;
    Uri imageUri;


    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    static final String USER = "user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_librarian);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name1);
        email = findViewById(R.id.email1);
        phone=findViewById(R.id.phone1);
        password = findViewById(R.id.password1);
        confirm_passwordEditText1 = findViewById(R.id.confirm_password1);
        profileImage = findViewById(R.id.profile_image);
        btn_addlibrarian1 = findViewById(R.id.btn_addlibrarian1);


        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();

        btn_addlibrarian1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name1 = name.getText().toString();
                String email1 = email.getText().toString().trim();
                String password1 = password.getText().toString().trim();
                String phone1=phone.getText().toString().trim();
                String confirm_password1 = confirm_passwordEditText1.getText().toString().trim();
                String profile_image = profileImage.toString().trim();

                // if (TextUtils.isEmpty(profile_image)) {
                //     Toast.makeText(getApplicationContext(), "please pick image", Toast.LENGTH_SHORT).show();
                //    return;
                // }

                if (TextUtils.isEmpty(name1)) {
                    Toast.makeText(getApplicationContext(), "please enter name", Toast.LENGTH_SHORT).show();
                }
               else if (TextUtils.isEmpty(phone1)) {
                    Toast.makeText(getApplicationContext(), "please enter PhoneNumber", Toast.LENGTH_SHORT).show();
                }
              else   if(phone1.length()<10){
                    Toast.makeText(getApplicationContext(), "Phone Number must be 10 digit", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(getApplicationContext(), "please enter emailId", Toast.LENGTH_SHORT).show();

                }

               else if (TextUtils.isEmpty(password1)) {
                    Toast.makeText(getApplicationContext(), "please enter password", Toast.LENGTH_SHORT).show();

                }
              else   if (TextUtils.isEmpty(confirm_password1)) {
                    Toast.makeText(getApplicationContext(), "please enter confirm password", Toast.LENGTH_SHORT).show();

                }

               else if (!confirm_password1.equals(password1)) {
                    confirm_passwordEditText1.setError("Password do not match");
                }
               else if(TextUtils.isEmpty(profile_image)){
                    Toast.makeText(getApplicationContext(), "please enter Image", Toast.LENGTH_SHORT).show();

                }
                else {
                    CreateUserAccount( email1,name1,password1);


                }

            }
        });

        //Image add to phone
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AddLibrarianActivity.this)
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
        //Image add to phone end


    }

    //add to email and password firebase
    private void CreateUserAccount(String email1, String name1, String password1) {

        mAuth.createUserWithEmailAndPassword(email1,password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                    }


                        else
                        {
                           // Toast.makeText(AddLibrarianActivity.this, "email already  used", Toast.LENGTH_SHORT).show();
                            if (imageUri != null) {
                                uploadImage();

                            } else {
                                Toast.makeText(AddLibrarianActivity.this, "please add photo", Toast.LENGTH_SHORT).show();


                            }
                        }


                    }
                });

    }
    //end to email and password firebase


    //upload image and other deatils to firebase
    private void uploadImage() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File Upload....");
        dialog.show();


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference("Image1" + new Random().nextInt(50));
        uploader.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Librarian librarian = new Librarian(
                                        name,
                                        email,
                                        password,
                                        phone,
                                        uri
                                        );


                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                String userid = firebaseUser.getUid();
                                mDatabase = FirebaseDatabase.getInstance().getReference("Librarian").child(userid);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", userid);
                                hashMap.put("Username",name.getText().toString());
                                hashMap.put("Email",email.getText().toString());
                                hashMap.put("Phonenumber",phone.getText().toString());
                                //hashMap.put("Password",password.getText().toString());
                                hashMap.put("Image",uri.toString());
                                mDatabase.setValue(hashMap);

                                name.setText("");
                                email.setText("");
                                password.setText("");
                                phone.setText("");
                                confirm_passwordEditText1.setText("");
                                profileImage.setImageResource(R.drawable.imageperson);


                            }
                        });


                        dialog.dismiss();
                        Toast.makeText(AddLibrarianActivity.this, "Fill uploaded", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        dialog.setMessage("uploaded:" + (int) percent + "%");


                    }
                });
    }
    //end upload image and other deatils to firebase


    //image add to phone
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null) {

            imageUri = data.getData();
            profileImage.setImageURI(imageUri);

        }
    }
    //image add to phone end
}








