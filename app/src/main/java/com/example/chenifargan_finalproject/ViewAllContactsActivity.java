package com.example.chenifargan_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllContactsActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://chenifarganfinalproject-default-rtdb.firebaseio.com");
    private String phoneNumber;
    //private ArrayList<String> arrayList = new ArrayList<String>();
    private ArrayList<person> arrayList = new ArrayList<person>();

    private RecyclerView recyclerView1;
    private RecyclerView main_LST_songs;

    private Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_contacts);
        phoneNumber = getIntent().getExtras().getString("phoneNumber");
        button = findViewById(R.id.btncli);
        main_LST_songs =findViewById(R.id.main_LST_songs);


        databaseReference.child("user").child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dss : snapshot.getChildren()) {
                    person p = dss.getValue(person.class);
                  //  String n = dss.getValue(String.class);
                    //arrayList.add(n);
                    arrayList.add(p);
                    Log.d("x", "chen ");
                    MyAdapter adapter_song = new MyAdapter(ViewAllContactsActivity.this, arrayList);
                    main_LST_songs.setLayoutManager(new LinearLayoutManager(ViewAllContactsActivity.this));
                    main_LST_songs.setAdapter(adapter_song);

                }


            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent =null;
            intent= new Intent(ViewAllContactsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
});

    }




}