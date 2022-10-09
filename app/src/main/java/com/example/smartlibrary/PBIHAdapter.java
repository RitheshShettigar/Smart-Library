package com.example.smartlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PBIHAdapter  extends RecyclerView.Adapter<PBIHAdapter.MyViewHolder>{
    Context context;

    ArrayList<modelPBIdetails> list;

    public PBIHAdapter(Context context, ArrayList<modelPBIdetails> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.principleissuebookreport_item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        modelPBIdetails modelPBIdetails=list.get(position);
        holder.bookid2.setText(modelPBIdetails.getBookId());
        holder.bookname2.setText(modelPBIdetails.getBookName());
        holder.bookreturn2.setText(modelPBIdetails.getReturnDate());
        holder.copies2.setText(modelPBIdetails.getCopies());
        holder.userid2.setText(modelPBIdetails.getUserId());
        holder.username2.setText(modelPBIdetails.getUserName());
        holder.usertype2.setText(modelPBIdetails.getUserType());
        holder.dept2.setText(modelPBIdetails.getDept());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView bookid2,bookname2,bookreturn2,copies2,userid2,username2,usertype2,dept2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookid2=(TextView) itemView.findViewById(R.id.boid4);
            bookname2=(TextView) itemView.findViewById(R.id.boname4);
            bookreturn2=(TextView) itemView.findViewById(R.id.boreturndate4);
            copies2=(TextView) itemView.findViewById(R.id.bocopiese4);
            userid2=(TextView) itemView.findViewById(R.id.uoid4);
            username2=(TextView) itemView.findViewById(R.id.uoname4);
            usertype2=(TextView) itemView.findViewById(R.id.uotype4);
            dept2=itemView.findViewById(R.id.depclass4);
        }
    }
}
