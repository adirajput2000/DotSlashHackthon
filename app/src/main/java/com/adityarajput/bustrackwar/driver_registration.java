package com.adityarajput.bustrackwar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class driver_registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);
        TextView registration = findViewById(R.id.textView5);
        Button registered = findViewById(R.id.button4);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //Upload data to database
        EditText uniquenumber = findViewById(R.id.editTextTextPersonName2);
        EditText busno = findViewById(R.id.editTextTextPersonName3);
        EditText password = findViewById(R.id.editTextTextPassword2);
        EditText noofseat = findViewById(R.id.editTextTextPersonName4);
        EditText startinglocation = findViewById(R.id.editTextTextPersonName5);
        EditText endinglocation = findViewById(R.id.editTextTextPersonName6);
        EditText drivername = findViewById(R.id.editTextTextPersonName7);
        EditText starttime = findViewById(R.id.editTextTextPersonName8);
        EditText email = findViewById(R.id.editTextTextPersonName9);
        EditText noofstations  = findViewById(R.id.editTextTextPersonName14);
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);


        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill = email.getText().toString();
                String passwordd = password.getText().toString();
                auth.createUserWithEmailAndPassword(emaill,passwordd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = auth.getCurrentUser();
                        String id = user.getUid();
                        String uniquenumberr = uniquenumber.getText().toString();
                        String busnoo = busno.getText().toString();

                        String noofseatt = noofseat.getText().toString();
                        String startinglocations = startinglocation.getText().toString();
                        String endinglocations = endinglocation.getText().toString();
                        String drivernamee = drivername.getText().toString();
                        String starttimee = starttime.getText().toString();
                        String noofstationss = noofstations.getText().toString();


                        database.getReference().child("userdetails").child(id).child("UniqueNumber").setValue(uniquenumberr);
                        database.getReference().child("userdetails").child(id).child("BusNumber").setValue(busnoo);
                        database.getReference().child("userdetails").child(id).child("Password").setValue(passwordd);
                        database.getReference().child("userdetails").child(id).child("NoonSeat").setValue(noofseatt);
                        database.getReference().child("userdetails").child(id).child("startinglocation").setValue(startinglocations);
                        database.getReference().child("userdetails").child(id).child("endinglocation").setValue(endinglocations);
                        database.getReference().child("userdetails").child(id).child("drivername").setValue(drivernamee);
                        database.getReference().child("userdetails").child(id).child("starttime").setValue(starttimee);
                        database.getReference().child("userdetails").child(id).child("id").setValue(id);
                        database.getReference().child("userdetails").child(id).child("noofstation").setValue(noofstationss);


                        Intent intent =  new Intent(view.getContext(),driver_app.class);
                        startActivity(intent);
                    }
                });

            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(view.getContext(), driver_app.class);
                startActivity(intent);
            }
        });
    }
}