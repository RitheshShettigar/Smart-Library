package com.example.smartlibrary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class SDMyAdapter extends RecyclerView.Adapter<SDMyAdapter.MyViewHolder> {

    Context context;

    ArrayList<modelSdetails>list;
    FirebaseUser firebaseUser;
    DatabaseReference mDatabase;

    public SDMyAdapter(Context context, ArrayList<modelSdetails> list) {
        this.context = context;
        this.list = list;
    }

    public SDMyAdapter(AccessControlContext context, ArrayList<modelSdetails> list, boolean b) {

        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.sditem,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        modelSdetails modelSdetails=list.get(position);
        holder.id.setText(modelSdetails.getId());
        holder.Username.setText(modelSdetails.getUsername());
        holder.EmailId.setText(modelSdetails.getEmailId());
        holder.Dept.setText(modelSdetails.getDeptC());
        holder.year.setText(modelSdetails.getYear());
        holder.type.setText(modelSdetails.getType());
        holder.PhoneNumber.setText(modelSdetails.getPhoneNumber());
       Glide.with(context).load(list.get(position).getImage()).into(holder.Image);



     holder.update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final DialogPlus dialogPlus=DialogPlus.newDialog(holder.Image.getContext())
                       .setContentHolder((new ViewHolder(R.layout.student_update_popup)))
                       .setExpanded(true,1200)
                       .create();



               View view1=dialogPlus.getHolderView();

               EditText id=view1.findViewById(R.id.txtid);
               EditText name=view1.findViewById(R.id.txtname);
               EditText type=view1.findViewById(R.id.txttype);
               EditText year=view1.findViewById(R.id.txtyear);
               EditText email=view1.findViewById(R.id.txtemail);
               EditText phone=view1.findViewById(R.id.txtphone);
               EditText surl=view1.findViewById(R.id.txtimage);
               EditText class1=view1.findViewById(R.id.txtclass);

               Button button=view1.findViewById(R.id.btnupdate);

               id.setText(modelSdetails.getId());
               name.setText(modelSdetails.getUsername());
               type.setText(modelSdetails.getType());
              year.setText(modelSdetails.getYear());
               email.setText(modelSdetails.getEmailId());
               phone.setText(modelSdetails.getPhoneNumber());
               class1.setText(modelSdetails.getDeptC());
               surl.setText(modelSdetails.getImage());

               dialogPlus.show();

             button.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Map<String,Object>map=new HashMap<>();
                       map.put("Id",id.getText().toString());
                       map.put("Username",name.getText().toString());
                       map.put("type",type.getText().toString());
                       map.put("year",year.getText().toString());
                       map.put("EmailId",email.getText().toString());
                       map.put("PhoneNumber",phone.getText().toString());
                       map.put("deptC",class1.getText().toString());
                       map.put("Image",surl.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child("Register")
                               .child(list.get(position).getId()).updateChildren(map)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       Toast.makeText(holder.Username.getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                       dialogPlus.dismiss();
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       Toast.makeText(holder.Username.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();

                                   }
                               });
                   }
               });



           }


       });

       holder.delete.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View view) {
               AlertDialog.Builder builder=new AlertDialog.Builder(holder.Username.getContext());
               builder.setTitle("Are you Sure?");
               builder.setMessage("Deleted data can't be Undo");

               builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {


                       FirebaseDatabase.getInstance().getReference().child("Register").child(list.get(position).getId())
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

        TextView  Username,EmailId,year,type,PhoneNumber,id,Dept;

        ImageView Image;
        ImageView update,delete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.tvid);
            Username=itemView.findViewById(R.id.tvname);
            EmailId=itemView.findViewById(R.id.tvemail);
            type=itemView.findViewById(R.id.tvtype);
            year=itemView.findViewById(R.id.tvyear);
            Dept=itemView.findViewById(R.id.tvdept);
            PhoneNumber=itemView.findViewById(R.id.tvphone);
            Image=itemView.findViewById(R.id.tvprofile);


         update=itemView.findViewById(R.id.update);
         delete=itemView.findViewById(R.id.delete);



        }
    }
}

