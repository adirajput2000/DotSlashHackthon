package com.adityarajput.bustrackwar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class passenger_app extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    my_passenger_adapter my_passenger_adapter;
    ArrayList<passenger_bus_details> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_app);

        recyclerView = findViewById(R.id.recycler);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("userdetails");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        my_passenger_adapter = new my_passenger_adapter(this, list);
        recyclerView.setAdapter(my_passenger_adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot :snapshot.getChildren())
                {
                    passenger_bus_details passenger_bus_details = dataSnapshot.getValue(com.adityarajput.bustrackwar.passenger_bus_details.class);
                    list.add(passenger_bus_details);

                }
                my_passenger_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}