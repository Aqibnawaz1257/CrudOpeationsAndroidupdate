package com.example.crudoperationinfirebasebyaqib;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<StdModel> models;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public MyAdapter(Context context, ArrayList<StdModel> models) {
        this.context = context;
        this.models = models;
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

        holder.name.setText(stdModel.getFirst_name());
        holder.email.setText(stdModel.getLast_name());
        holder.phone.setText(stdModel.getEmail());
        holder.password.setText(stdModel.getUsername());

        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Update_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                intent.putExtra("id",stdModel.getSt_id());
                intent.putExtra("fname",stdModel.getFirst_name());
                intent.putExtra("lname",stdModel.getLast_name());
                intent.putExtra("email",stdModel.getEmail());
                intent.putExtra("username",stdModel.getUsername());

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


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.showname);
            email = itemView.findViewById(R.id.showemail);
            phone = itemView.findViewById(R.id.showphone);
            password = itemView.findViewById(R.id.showpass);

            btn_update = itemView.findViewById(R.id.bt_update);
            btn_delete = itemView.findViewById(R.id.btn_delete);




        }
    }
}
