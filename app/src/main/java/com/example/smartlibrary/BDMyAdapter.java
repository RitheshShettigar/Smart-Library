package com.example.smartlibrary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.security.AccessControlContext;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BDMyAdapter extends RecyclerView.Adapter<BDMyAdapter.MyViewHolder>{
    public Object startL;
    Context context;

    ArrayList<modelBdetails> list;


    public BDMyAdapter(Context context, ArrayList<modelBdetails> list) {
        this.context = context;
        this.list = list;

    }

    public BDMyAdapter(FirebaseRecyclerOptions<modelBdetails> options) {


    }

    public BDMyAdapter(Object context, ArrayList<modelBdetails> list, boolean b) {

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.bditem,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        modelBdetails modelBdetails=list.get(position);
        holder.bookId.setText(modelBdetails.getBookId());
        holder.bookName.setText(modelBdetails.getBookName());
        holder.bookAuthor.setText(modelBdetails.getBookAuthor());
        holder.bookPublication.setText(modelBdetails.getBookPublication());
        holder.bookYear.setText(modelBdetails.getBookYear());
        holder.bookQuantity.setText(modelBdetails.getBookQuantity());
        holder.bookCategory.setText(modelBdetails.getBookCategory());
        holder.bookLanguage.setText(modelBdetails.getBookLanguage());



        Glide.with(context).load(list.get(position).getImage()).into(holder.profileImage);

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.profileImage.getContext())
                        .setContentHolder((new ViewHolder(R.layout.book_update_popup)))
                        .setExpanded(false,2500)
                        .create();



                View view1=dialogPlus.getHolderView();

                EditText bookid=view1.findViewById(R.id.txtid);
                EditText bookname=view1.findViewById(R.id.txtbname);
                EditText bookauthor=view1.findViewById(R.id.txtbauthor);
                EditText bookpublication=view1.findViewById(R.id.txtbpublication);
                EditText bookyear=view1.findViewById(R.id.txtbyearl);
                EditText bookquantity=view1.findViewById(R.id.txtbquantity);
                EditText bookcategory=view1.findViewById(R.id.txtbcategory);
                EditText booklanguage=view1.findViewById(R.id.txtblanguage);
                EditText surl=view1.findViewById(R.id.txtbimage);

                Button button=view1.findViewById(R.id.btnupdate);

                bookid.setText(modelBdetails.getBookId());
                bookauthor.setText(modelBdetails.getBookAuthor());
                bookname.setText(modelBdetails.getBookName());
                bookpublication.setText(modelBdetails.getBookPublication());
                bookyear.setText(modelBdetails.getBookYear());
                bookquantity.setText(modelBdetails.getBookQuantity());
                bookcategory.setText(modelBdetails.getBookCategory());
                booklanguage.setText(modelBdetails.getBookLanguage());
                surl.setText(modelBdetails.getImage());

                dialogPlus.show();

              button.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Map<String,Object> map=new HashMap<>();
                       map.put("BookId",bookid.getText().toString());
                       map.put("BookName",bookname.getText().toString());
                       map.put("BookAuthor",bookauthor.getText().toString());
                       map.put("BookCategory",bookcategory.getText().toString());
                       map.put("BookLanguage",booklanguage.getText().toString());
                       map.put("BookPublication",bookpublication.getText().toString());
                       map.put("BookQuantity",bookquantity.getText().toString());
                       map.put("BookYear",bookyear.getText().toString());
                       map.put("Image",surl.getText().toString());

                       FirebaseDatabase.getInstance().getReference().child("Book")
                               .child(list.get(position).getBookId()).updateChildren(map)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       Toast.makeText(holder.bookId.getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                       dialogPlus.dismiss();
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       Toast.makeText(holder.bookId.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();

                                   }
                               });
                   }
               });



            }
        });





        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.bookName.getContext());
                builder.setTitle("Are you Sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("Book").child(list.get(position).getBookId())
                                .removeValue()

                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(holder.bookName.getContext(), "deleted", Toast.LENGTH_SHORT).show();


                                    }
                                });





                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.bookName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView bookId,bookName, bookAuthor, bookPublication, bookYear, bookQuantity, bookCategory, bookDepartment, bookLanguage;
       ImageView profileImage;
        ImageView update,delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bookId=(TextView) itemView.findViewById(R.id.bid);
            bookName=(TextView) itemView.findViewById(R.id.bbookname);
           bookAuthor=(TextView) itemView.findViewById(R.id.bbookauthor);
           bookPublication=(TextView) itemView.findViewById(R.id.bbookpublication);
           bookYear=(TextView) itemView.findViewById(R.id.bbookyear);
           bookQuantity=(TextView) itemView.findViewById(R.id.bbookquantity);
           bookCategory=(TextView) itemView.findViewById(R.id.bbookcategory);
           //bookDepartment=(TextView) itemView.findViewById(R.id.bbookdepartment);
            bookLanguage=(TextView) itemView.findViewById(R.id.bbooklanguage);
            profileImage=(ImageView) itemView.findViewById(R.id.tvprofile);

            update=itemView.findViewById(R.id.update1);
            delete=itemView.findViewById(R.id.delete1);
        }
    }

}

