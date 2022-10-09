package com.example.smartlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class BDSMyAdapter extends RecyclerView.Adapter<BDSMyAdapter.MyViewHolder>{
    public Object startL;
    Context context;



    ArrayList<modelBdetails> list;

    public BDSMyAdapter(Context context, ArrayList<modelBdetails> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.bditemstudent,parent,false);
        return  new BDSMyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        modelBdetails modelBdetails=list.get(position);
        holder.bookId.setText(modelBdetails.getBookId());
        holder.bookName.setText(modelBdetails.getBookName());
        holder.bookAuthor.setText(modelBdetails.getBookAuthor());
        holder.bookPublication.setText(modelBdetails.getBookPublication());
        holder.bookYear.setText(modelBdetails.getBookYear());
        holder.bookQuantity.setText(modelBdetails.getBookQuantity());
        holder.bookCategory.setText(modelBdetails.getBookCategory());
        holder.bookLanguage.setText(modelBdetails.getBookLanguage());





        Intent intent=((Activity)context).getIntent();
        String uname,uid,utype,dept1;
        uname =intent.getStringExtra("name1");
        uid=intent.getStringExtra("id1");
       utype=intent.getStringExtra("type1");
       dept1=intent.getStringExtra("department");
        Intent intent6=((Activity)context).getIntent();


        Glide.with(context).load(list.get(position).getImage()).into(holder.profileImage);

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(context,BookOrderActivity.class);


                intent.putExtra("id3",holder.bookId.getText().toString());
                intent.putExtra("name3",holder.bookName.getText().toString());
               //Toast.makeText(context,holder.bookId.getText().toString() , Toast.LENGTH_SHORT).show();
                intent.putExtra("id4",uid.toString());
                intent.putExtra("name4",uname.toString());
                intent.putExtra("type4",utype.toString());
                intent.putExtra("dept4",dept1.toString());
                context.startActivity(intent);
                //Toast.makeText(context, uid, Toast.LENGTH_SHORT).show();


            }
        });

        DatabaseReference root= FirebaseDatabase.getInstance().getReference("Order");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(uid).exists()){
                    holder.order.setVisibility(View.GONE);
                }
                else {
                    holder.order.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        ImageView order;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookId=(TextView) itemView.findViewById(R.id.bid);
            bookName=(TextView) itemView.findViewById(R.id.bbookname);
            bookAuthor=(TextView) itemView.findViewById(R.id.bbookauthor);
            bookPublication=(TextView) itemView.findViewById(R.id.bbookpublication);
            bookYear=(TextView) itemView.findViewById(R.id.bbookyear);
            bookQuantity=(TextView) itemView.findViewById(R.id.bbookquantity);
            bookCategory=(TextView) itemView.findViewById(R.id.bbookcategory);
            bookLanguage=(TextView) itemView.findViewById(R.id.bbooklanguage);
            profileImage=(ImageView) itemView.findViewById(R.id.tvprofile);
            order=(ImageView) itemView.findViewById(R.id.order);




        }
    }
}
