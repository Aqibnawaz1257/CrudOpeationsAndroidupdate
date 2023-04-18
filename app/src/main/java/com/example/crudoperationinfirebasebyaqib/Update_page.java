package com.example.crudoperationinfirebasebyaqib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Update_page extends AppCompatActivity {


    EditText name , email , phone ,lname;
    Button add;
    String Sname , Semail , Sphone , Spass , SLname;

    DatabaseReference db ;

    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_page);
        init();
    }

    private void init(){

        name = findViewById(R.id.uedtname);
        email = findViewById(R.id.uedtemail);
        phone = findViewById(R.id.uedtphone);
        lname = findViewById(R.id.uedtlname);


        dialog = new ProgressDialog(this);
        dialog.setTitle("User Update");
        dialog.setMessage("please wait User updating..!");


        add = findViewById(R.id.btn_Update);

        Intent intent = getIntent();

        String ssid = intent.getStringExtra("id");
        String ssname = intent.getStringExtra("fname");
        String ssemail = intent.getStringExtra("email");
        String ssphone = intent.getStringExtra("username");
        String sspass = intent.getStringExtra("password");
        String sslname = intent.getStringExtra("lname");


        name.setText(ssname);
        email.setText(ssemail);
        phone.setText(ssphone);
        lname.setText(sslname);



        db = FirebaseDatabase.getInstance().getReference().child("Students_Info");


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
                getValidations(view,ssid);
            }
        });

    }

    private void getValidations(View view , String id){

        Sname = name.getText().toString();
        Semail = email.getText().toString();
        Sphone = phone.getText().toString();
        SLname = lname.getText().toString();


        if (Sname.isEmpty()){
            dialog.dismiss();
            Snackbar.make(view , "please enter your first name " , Snackbar.LENGTH_LONG).show();
        } else if (SLname.isEmpty()) {
            dialog.dismiss();

            Snackbar.make(view , "please enter your last name " , Snackbar.LENGTH_LONG).show();
        }else if (Semail.isEmpty()) {
            dialog.dismiss();

            Snackbar.make(view , "please enter your email " , Snackbar.LENGTH_LONG).show();
        }else if (Sphone.isEmpty()) {
            dialog.dismiss();

            Snackbar.make(view , "please enter your username " , Snackbar.LENGTH_LONG).show();
        }else {

            db.child(id).setValue(new StdModel(id,Sname,SLname,Semail,Sphone));
            Snackbar.make(view , "Update Data Successfully" , Snackbar.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            Clear();
            dialog.dismiss();


        }


    }


    private void Clear(){

        name.setText("");
        email.setText("");
        phone.setText("");
        lname.setText("");

    }



}