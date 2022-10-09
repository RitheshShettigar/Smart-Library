package com.example.smartlibrary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class BRAMyAdapter  extends RecyclerView.Adapter<BRAMyAdapter.MyViewHolder>{
    Context context;

    ArrayList<modelBRdetails> list;


    DatabaseReference mDatabase;
    static  final String Book="book";
    private FirebaseAuth mAuth;
    FirebaseDatabase database;




    public BRAMyAdapter(Context context, ArrayList<modelBRdetails> list) {
        this.context = context;
        this.list = list;

        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.brl,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        modelBRdetails modelBRdetails=list.get(position);
        holder.bookid3.setText(modelBRdetails.getBookId());
        holder.bookname3.setText(modelBRdetails.getBookName());
        holder.bookreturn3.setText(modelBRdetails.getReturnDate());
        holder.copies3.setText(modelBRdetails.getCopies());
        holder.userid3.setText(modelBRdetails.getUserId());
        holder.username3.setText(modelBRdetails.getUserName());
        holder.usertype3.setText(modelBRdetails.getUserType());
        holder.dept3.setText(modelBRdetails.getDept());


        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // holder.layout.setBackgroundColor(Color.BLACK);

                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                String id = firebaseUser.getUid();
                mDatabase = FirebaseDatabase.getInstance().getReference("Approved").child(holder.userid3.getText().toString());
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put("id", id);
                hashMap.put("BookId",holder.bookid3.getText().toString());
                hashMap.put("UserId",holder.userid3.getText().toString());


                mDatabase.setValue(hashMap);
                Toast.makeText(context, "Approved", Toast.LENGTH_SHORT).show();



            }
        });



        mDatabase = FirebaseDatabase.getInstance().getReference("Approved");
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(modelBRdetails.UserId).exists()){
                    holder.approve.setVisibility(View.GONE);
                    holder.check.setImageResource(R.drawable.check6);

                }
                else {
                    holder.approve.setVisibility(View.VISIBLE);
                    holder.check.setImageResource(R.drawable.close);

                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


                holder.return1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.userid3.getContext());
                        builder.setTitle("Are you Sure?");
                        builder.setMessage("Return the Book");

                        builder.setPositiveButton("return", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase.getInstance().getReference().child("Approved").child(list.get(position).getUserId())
                                        .removeValue()

                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                               // Toast.makeText(holder.userid3.getContext(), "return book Successful", Toast.LENGTH_SHORT).show();


                                            }
                                        });

                                FirebaseDatabase.getInstance().getReference().child("Order").child(list.get(position).getUserId())

                                        .removeValue()

                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(holder.userid3.getContext(), "return book Successful", Toast.LENGTH_SHORT).show();


                                            }
                                        });


                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(holder.bookid3.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

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
        TextView bookid3,bookname3,bookreturn3,copies3,userid3,username3,usertype3,dept3;
        Button approve,return1;
        CardView layout;
        ImageView check;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bookid3=(TextView) itemView.findViewById(R.id.boid);
            bookname3=(TextView) itemView.findViewById(R.id.boname);
            bookreturn3=(TextView) itemView.findViewById(R.id.boreturndate);
            copies3=(TextView) itemView.findViewById(R.id.bocopiese);
            userid3=(TextView) itemView.findViewById(R.id.uoid);
            username3=(TextView) itemView.findViewById(R.id.uoname);
            usertype3=(TextView) itemView.findViewById(R.id.uotype);
            approve=(Button) itemView.findViewById(R.id.approved);
            return1=(Button) itemView.findViewById(R.id.return1);
            layout=(CardView) itemView.findViewById(R.id.linearlayout);
            dept3=itemView.findViewById(R.id.depclass);
            check=(ImageView) itemView.findViewById(R.id.chech1);



        }
    }

}
