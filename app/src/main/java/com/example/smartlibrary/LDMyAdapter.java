package com.example.smartlibrary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LDMyAdapter extends RecyclerView.Adapter<LDMyAdapter.MyViewHolder>{
    Context context;

    ArrayList<modelLdetails> list;

    public LDMyAdapter(Context context, ArrayList<modelLdetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.lditem,parent,false);
        return  new LDMyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,@SuppressLint("RecyclerView") int position) {
        modelLdetails modelLdetails=list.get(position);

        holder.Username.setText(modelLdetails.getUsername());
        holder.Email.setText(modelLdetails.getEmail());
        holder.Phonenumber.setText(modelLdetails.getPhonenumber());

        Glide.with(context).load(list.get(position).getImage()).into(holder.Image);


        holder.delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.Username.getContext());
                builder.setTitle("Are you Sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        FirebaseDatabase.getInstance().getReference().child("Librarian")
                                .removeValue()

                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(holder.Username.getContext(), "deleted", Toast.LENGTH_SHORT).show();


                                    }
                                });


                    }



                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.Username.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

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
        TextView Email,Phonenumber,Username;

        ImageView Image;
        ImageView delete2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Email=(TextView) itemView.findViewById(R.id.libemail);
            Phonenumber=(TextView) itemView.findViewById(R.id.libnumber);
            Username=(TextView) itemView.findViewById(R.id.libname);
            Image =(ImageView) itemView.findViewById(R.id.libprofile);
            delete2=itemView.findViewById(R.id.delete2);
        }
    }
}
