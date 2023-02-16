package com.example.crudoperationinfirebasebyaqib;

import androidx.appcompat.app.AppCompatActivity;

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


    EditText name , email , phone , password;
    Button add;
    String Sname , Semail , Sphone , Spass , id;

    DatabaseReference db ;


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
        password = findViewById(R.id.uedtpass);

        add = findViewById(R.id.btn_Update);

        Intent intent = getIntent();

        String ssid = intent.getStringExtra("id");
        String ssname = intent.getStringExtra("name");
        String ssemail = intent.getStringExtra("email");
        String ssphone = intent.getStringExtra("phone");
        String sspass = intent.getStringExtra("password");


        name.setText(ssname);
        email.setText(ssemail);
        phone.setText(ssphone);
        password.setText(sspass);



        db = FirebaseDatabase.getInstance().getReference().child("Students_Info");


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getValidations(view,ssid);
            }
        });

    }

    private void getValidations(View view , String id){

        Sname = name.getText().toString();
        Semail = email.getText().toString();
        Sphone = phone.getText().toString();
        Spass = password.getText().toString();


        if (Sname.isEmpty()){
            Snackbar.make(view , "please enter your name " , Snackbar.LENGTH_LONG).show();
        } else if (Semail.isEmpty()) {
            Snackbar.make(view , "please enter your email " , Snackbar.LENGTH_LONG).show();
        }else if (Sphone.isEmpty()) {
            Snackbar.make(view , "please enter your phone " , Snackbar.LENGTH_LONG).show();
        }else if (Spass.isEmpty()) {
            Snackbar.make(view , "please enter your password " , Snackbar.LENGTH_LONG).show();
        }else {

            db.child(id).setValue(new StdModel(id,Sname,Semail,Sphone,Spass));
            Snackbar.make(view , "Update Data " , Snackbar.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            Clear();

        }


    }


    private void Clear(){

        name.setText("");
        email.setText("");
        phone.setText("");
        password.setText("");

    }



}