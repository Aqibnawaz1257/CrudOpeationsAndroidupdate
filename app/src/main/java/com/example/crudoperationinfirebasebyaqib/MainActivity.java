package com.example.crudoperationinfirebasebyaqib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    MyAdapter adapter;
    ArrayList<StdModel> arrayList = new ArrayList<>();

    DatabaseReference db ;
    Button btnAdd;

    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.showdata);
        recyclerView.hasFixedSize();
        btnAdd = findViewById(R.id.adddata);

        searchView = findViewById(R.id.searchview);

        seachbutton();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), InsertData.class));

            }
        });

        db = FirebaseDatabase.getInstance().getReference().child("Students_Info");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    StdModel model = dataSnapshot.getValue(StdModel.class);
                    arrayList.add(model);
                }
                adapter = new MyAdapter(getApplicationContext(),arrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    private void seachbutton(){

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                fliterlist(newText);
                return true;
            }
        });


    }

    private void fliterlist(String newText) {

        ArrayList<StdModel> fliterlist = new ArrayList<>();
        for (StdModel model : arrayList){

            if(model.getName().toLowerCase(Locale.ROOT).contains(newText) || model.getName().toUpperCase(Locale.ROOT).contains(newText))
            {
                fliterlist.add(model);
            }
        }

        if(fliterlist.isEmpty())
        {
            adapter.setfilterlist(fliterlist);
            Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter.setfilterlist(fliterlist);

        }

    }



}