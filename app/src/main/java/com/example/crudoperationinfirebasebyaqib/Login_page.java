package com.example.crudoperationinfirebasebyaqib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Login_page extends AppCompatActivity {


    EditText  email , password;
    Button login;
    String  Semail ,  Spass;

    FirebaseAuth mAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        init();
    }
    public void regg(View view) {
        startActivity(new Intent(getApplicationContext(),InsertData.class));
        finish();
    }

    private void init(){
        email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpass);
        login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getValidations(view);
            }
        });

    }
    private void getValidations(View view){

        Semail = email.getText().toString();
        Spass = password.getText().toString();

        if (Semail.equals("admin@gmail.com") && Spass.equals("123456")){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        if (Semail.isEmpty()) {
            Snackbar.make(view , "please enter your email " , Snackbar.LENGTH_LONG).show();
        }else if (Spass.isEmpty()) {
            Snackbar.make(view , "please enter your password " , Snackbar.LENGTH_LONG).show();
        }else {
            mAuth.signInWithEmailAndPassword(Semail,Spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        user = mAuth.getCurrentUser();
                        System.out.println(user.getUid());
                        Clear();
                        Intent in= new Intent(getApplicationContext(),ViewProfile.class);
                        in.putExtra("uid",user.getUid());
                        startActivity(in);
                        finish();

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

        email.setText("");
        password.setText("");

    }





}