package com.example.crudoperationinfirebasebyaqib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewProfile extends AppCompatActivity {

    TextView name , email , phone , lname;
    EditText upname , upemail , upphone,uplastname;

    LinearLayout layout;

    Button edit , delete_account , btn_update_profile;

    DatabaseReference db ;

    FirebaseAuth mAuth;
    FirebaseUser user;

    ProgressDialog dialog;

    String Sname , Semail , Sphone , Slname;
    StdModel stdModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        init();

        Intent intent = getIntent();

        String uid = intent.getStringExtra("uid");

        System.out.println("my id "+uid);

        db.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                StdModel model = snapshot.getValue(StdModel.class);

                if (model != null){

                    Sname = model.getFirst_name();
                    Semail = user.getEmail();
                    Sphone = model.getUsername();
                    Slname = model.getLast_name();


                    name.setText(Sname);
                    email.setText(Semail);
                    phone.setText(Sphone);
                    lname.setText(Slname);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init(){

        name = findViewById(R.id.showusername);
        lname = findViewById(R.id.showuserlname);
        email = findViewById(R.id.showuseremail);
        phone = findViewById(R.id.showuserphone);
        btn_update_profile = findViewById(R.id.btn_update_profile);
        delete_account = findViewById(R.id.btn_delete_account);


        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait");
        dialog.setMessage("please wait the process is working..!");



        upname = findViewById(R.id.upname);
        upemail = findViewById(R.id.upemail);
        upphone = findViewById(R.id.upphone);
        uplastname = findViewById(R.id.uplname);

        layout = findViewById(R.id.updateprofilelayout);
        edit = findViewById(R.id.bt_edit_profile);

        db = FirebaseDatabase.getInstance().getReference().child("Students_Info");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                db.child(user.getUid()).removeValue();
                AuthCredential credential = EmailAuthProvider.getCredential(email.getText().toString(),"123456");
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            user.delete();
                            startActivity(new Intent(getApplicationContext(),Login_page.class));
                            Snackbar.make(v,"Delete Account Successfully",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

                Snackbar.make(v , "Delete Account Successfully " , Snackbar.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),Login_page.class);
                startActivity(intent);
                finish();
                dialog.dismiss();



            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upname.setText(Sname);
                upemail.setText(Semail);
                upphone.setText(Sphone);
                uplastname.setText(Slname);
                layout.setVisibility(View.VISIBLE);




            }
        });


        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                 stdModel= new StdModel(user.getUid(),
                         upname.getText().toString(),
                         uplastname.getText().toString(),
                        upemail.getText().toString(),
                         upphone.getText().toString());
                db.child(user.getUid()).setValue(stdModel);
                layout.setVisibility(View.GONE);

                Clear();
                Snackbar.make(v , "User profile Updated " , Snackbar.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),Login_page.class);
                startActivity(intent);
                finish();
                dialog.dismiss();




            }
        });






    }

    private void Clear(){

        upname.setText("");
        upemail.setText("");
        upphone.setText("");
        uplastname.setText("");

    }


    public void logout(View view) {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(),Login_page.class));
        finish();
    }
}