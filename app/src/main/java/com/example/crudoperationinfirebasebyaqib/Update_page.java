package com.example.crudoperationinfirebasebyaqib;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class Update_page extends AppCompatActivity {


    EditText name , email , phone , password;
    Button add;
    String Sname , Semail , Sphone , Spass , id;

    DatabaseReference db ;
    ImageView btnopengallery;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;


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

        btnopengallery = findViewById(R.id.opengallery);

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




        btnopengallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnopengallery();
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageUri != null){
                    uploadToFirebase(imageUri,view,ssid);
                }else{
                    Toast.makeText(getApplicationContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            btnopengallery.setImageURI(imageUri);

        }

    }


    private void uploadToFirebase(Uri uri , View view , String id){

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        getValidations(view,id,uri);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }





    void setBtnopengallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent , 2);
    }

    private void getValidations(View view , String id , Uri uri){

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

            db.child(id).setValue(new StdModel(id,Sname,Semail,Sphone,Spass,uri.toString()));
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
        btnopengallery.setImageResource(R.drawable.round_account_circle_24);
    }



}