package com.example.smartlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LPDMyAdapter  extends RecyclerView.Adapter<LPDMyAdapter.MyViewHolder>{
    Context context;

    ArrayList<modelLdetails> list;

    public LPDMyAdapter(Context context, ArrayList<modelLdetails> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.profilelibrarian,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      /*  modelLdetails modelLdetails=list.get(position);

        holder.Username.setText(modelLdetails.getUsername());
        holder.Email.setText(modelLdetails.getEmail());
        holder.Phonenumber.setText(modelLdetails.getPhonenumber());

        Glide.with(context).load(list.get(position).getImage()).into(holder.Image);*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
       // TextView Email,Phonenumber,Username;

       // ImageView Image;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
             /* Email=(TextView) itemView.findViewById(R.id.libemail);
            Phonenumber=(TextView) itemView.findViewById(R.id.libnumber);
            Username=(TextView) itemView.findViewById(R.id.libname);
            Image =(ImageView) itemView.findViewById(R.id.libprofile);*/


        }
    }

}

