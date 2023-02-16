package com.example.crudoperationinfirebasebyaqib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class InsertData extends AppCompatActivity {


    EditText name , email , phone , password;
    Button add;
    String Sname , Semail , Sphone , Spass , id;

    DatabaseReference db ;
    FirebaseAuth mAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        init();
    }


    private void init(){

        name = findViewById(R.id.edtname);
        email = findViewById(R.id.edtemail);
        phone = findViewById(R.id.edtphone);
        password = findViewById(R.id.edtpass);

        add = findViewById(R.id.btn_insert);

        db = FirebaseDatabase.getInstance().getReference().child("Students_Info");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getValidations(view);
            }
        });

    }

    private void getValidations(View view){

        Sname = name.getText().toString();
        Semail = email.getText().toString();
        Sphone = phone.getText().toString();
        Spass = password.getText().toString();
//        id = "Student"+ new Date().getTime();




        if (Sname.isEmpty()){
            Snackbar.make(view , "please enter your name " , Snackbar.LENGTH_LONG).show();
        } else if (Semail.isEmpty()) {
            Snackbar.make(view , "please enter your email " , Snackbar.LENGTH_LONG).show();
        }else if (Sphone.isEmpty()) {
            Snackbar.make(view , "please enter your phone " , Snackbar.LENGTH_LONG).show();
        }else if (Spass.isEmpty()) {
            Snackbar.make(view , "please enter your password " , Snackbar.LENGTH_LONG).show();
        }else {


            mAuth.createUserWithEmailAndPassword(Semail,Spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()){
                        id = user.getUid();

                        StdModel model = new StdModel(id,Sname,Semail,Sphone,Spass);
                        db.child(id).setValue(model);
                        Snackbar.make(view , "User Account created Successfully " , Snackbar.LENGTH_LONG).show();
                        Clear();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent = new Intent(getApplicationContext(),Login_page.class);
                                intent.putExtra("id",id);
                                startActivity(intent);



                            }
                        },3000);


                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Snackbar.make(view ,  e.getMessage().toString() , Snackbar.LENGTH_LONG).show();
                }
            });




        }


    }


    private void Clear(){

        name.setText("");
        email.setText("");
        phone.setText("");
        password.setText("");

    }



}

