package com.example.crudoperationinfirebasebyaqib;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<StdModel> models;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public MyAdapter(Context context, ArrayList<StdModel> models) {
        this.context = context;
        this.models = models;
    }


    public void setfilterlist(ArrayList<StdModel> newlist){

        this.models=newlist;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        StdModel stdModel = models.get(position);

        holder.name.setText(stdModel.getName());
        holder.email.setText(stdModel.getEmail());
        holder.phone.setText(stdModel.getPhone());
        holder.password.setText(stdModel.getPassword());


        if (!stdModel.getImg_url().equals("")){
            Picasso.get().load(stdModel.getImg_url()).into(holder.showprofileimg);
        }else {
            holder.showprofileimg.setImageResource(R.drawable.round_account_circle_24);

        }



        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Update_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                intent.putExtra("id",stdModel.getSt_id());
                intent.putExtra("name",stdModel.getName());
                intent.putExtra("email",stdModel.getEmail());
                intent.putExtra("phone",stdModel.getPhone());
                intent.putExtra("password",stdModel.getPassword());

                context.startActivity(intent);

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.child("Students_Info").child(stdModel.getSt_id()).removeValue();
                Snackbar.make(view,"Delete Data Successfully",Snackbar.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name , email , phone , password;
        Button btn_update , btn_delete;

        ImageView showprofileimg;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.showname);
            email = itemView.findViewById(R.id.showemail);
            phone = itemView.findViewById(R.id.showphone);
            password = itemView.findViewById(R.id.showpass);

            showprofileimg = itemView.findViewById(R.id.profileshowimg);

            btn_update = itemView.findViewById(R.id.bt_update);
            btn_delete = itemView.findViewById(R.id.btn_delete);




        }
    }
}
