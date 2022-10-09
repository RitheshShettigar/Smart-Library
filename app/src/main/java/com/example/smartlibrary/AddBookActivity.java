package com.example.smartlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class AddBookActivity extends AppCompatActivity {

    EditText book_id,book_name,book_author,book_publication,book_year,book_quantity,book_category,book_department,book_language;
    Button btn_addbook;
    ImageView profile;
    Uri filepath;


    DatabaseReference mDatabase;
    static  final String Book="book";
    private FirebaseAuth mAuth;
    FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        book_name=findViewById(R.id.bookname1);
        book_id=findViewById(R.id.bookID1);
        book_author=findViewById(R.id.bookauthor1);
        book_publication=findViewById(R.id.bookpublication1);
        book_year=findViewById(R.id.bookyear1);
        book_quantity=findViewById(R.id.bookquantity1);
        book_category=findViewById(R.id.bookcategiry1);
        book_language=findViewById(R.id.booklanguage1);
        btn_addbook=findViewById(R.id.btn_addbook);
        profile=findViewById(R.id.profile1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database=FirebaseDatabase.getInstance();
        //  mDatabase=database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();


        btn_addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String bookid1=book_id.getText().toString().trim();
                String bookname1=book_name.getText().toString().trim();
                String bookauthor1=book_author.getText().toString().trim();
                String bookpublication1=book_publication.getText().toString().trim();
                String bookyear1=book_year.getText().toString().trim();
                String bookquantity1=book_quantity.getText().toString().trim();
                String bookcategory1=book_category.getText().toString().trim();
                String booklanguage1=book_language.getText().toString().trim();
                String profile1=profile.toString().trim();


                if(TextUtils.isEmpty(bookid1)) {
                    Toast.makeText(getApplicationContext(), "Enter Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(bookname1)) {
                    Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(TextUtils.isEmpty(bookauthor1)) {

                    Toast.makeText(getApplicationContext(), "Enter bookAuthor", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(bookpublication1)) {
                    Toast.makeText(getApplicationContext(), "Enter Publication", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(bookyear1)){
                    Toast.makeText(getApplicationContext(), "Enter bookYear", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(bookquantity1)) {
                    Toast.makeText(getApplicationContext(), "Enter bookQuantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(bookcategory1) ){
                    Toast.makeText(getApplicationContext(), "Enter bookCategory", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(booklanguage1)) {
                    Toast.makeText(getApplicationContext(), "Enter bookLanguage", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(profile1)){
                    Toast.makeText(getApplicationContext(), "Enter profile", Toast.LENGTH_SHORT).show();
                    return;

                }  else if (filepath!=null){

                    uploadToFirebase();
                } else {
                    Toast.makeText(AddBookActivity.this, "please add Photo", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // select image to gallery
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AddBookActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent,100);
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
        // select image to gallery





    }
    //file upload to firebase
    private void uploadToFirebase() {
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Upload....");
        dialog.show();


        book_name=findViewById(R.id.bookname1);
        book_id=findViewById(R.id.bookID1);
        book_author=findViewById(R.id.bookauthor1);
        book_publication=findViewById(R.id.bookpublication1);
        book_year=findViewById(R.id.bookyear1);
        book_quantity=findViewById(R.id.bookquantity1);
        book_category=findViewById(R.id.bookcategiry1);
        book_language=findViewById(R.id.booklanguage1);

        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploader=storage.getReference("Image"+new Random().nextInt(50));
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Book book = new Book(
                                        book_id,
                                        book_name,
                                        book_author,
                                        book_publication,
                                        book_year,
                                        book_quantity,
                                        book_category,
                                        book_language,
                                        uri
                                );
                                //FirebaseDatabase db = FirebaseDatabase.getInstance();
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                String id = firebaseUser.getUid();
                                mDatabase = FirebaseDatabase.getInstance().getReference("Book").child(book_id.getText().toString());
                                HashMap<String, String> hashMap = new HashMap<>();

                                hashMap.put("id", id);
                                hashMap.put("BookId",book_id.getText().toString());
                                hashMap.put("BookName",book_name.getText().toString());
                                hashMap.put("BookAuthor",book_author.getText().toString());
                                hashMap.put("BookPublication",book_publication.getText().toString());
                                hashMap.put("BookYear", book_year.getText().toString());
                                hashMap.put("BookQuantity", book_quantity.getText().toString());
                                hashMap.put("BookCategory", book_category.getText().toString());
                                hashMap.put("BookLanguage", book_language.getText().toString());
                                hashMap.put("Image",uri.toString());
                                mDatabase.setValue(hashMap);



                                book_id.setText("");
                                book_name.setText("");
                                book_author.setText("");
                                book_publication.setText("");
                                book_year.setText("");
                                book_quantity.setText("");
                                book_category.setText("");
                                book_language.setText("");
                                profile.setImageResource(R.drawable.imageperson);


                            }
                        });




                        dialog.dismiss();
                        Toast.makeText(AddBookActivity.this, "Book uploaded", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        dialog.setMessage("uploaded:"+(int)percent+"%");


                    }
                });


    }
    //file upload to firebase end

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&data!=null&&data.getData()!=null){

            filepath=data.getData();
            profile.setImageURI(filepath);

        }

    }
}