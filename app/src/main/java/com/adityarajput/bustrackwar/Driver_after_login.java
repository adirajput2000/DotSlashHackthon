package com.adityarajput.bustrackwar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver_after_login extends AppCompatActivity {
    String noofsation;
    String check;

    private  static final int REQUEST_CODE_LOCATION_PERMISSION =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_after_login);

        Switch locationbutton  = findViewById(R.id.switch1);
        if(ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Driver_after_login.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION_PERMISSION);
        }
        else{
            startLocationService();
            locationbutton.setChecked(true);

        }
        Button sendmessage  = findViewById(R.id.button7);
        Button remainseat = findViewById(R.id.button6);
        TextView uniqueno = findViewById(R.id.textView17);
        TextView vehicleno = findViewById(R.id.textView18);
        TextView vehiclenos = findViewById(R.id.textView9);
        TextView noofseat = findViewById(R.id.textView19);
        TextView startinglocation = findViewById(R.id.textView20);
        TextView endlocation = findViewById(R.id.textView21);
        TextView drivername = findViewById(R.id.textView22);
        TextView starttime = findViewById(R.id.textView23);
        TextView tesi = findViewById(R.id.textView53);
        TextView textView = findViewById(R.id.textView54);
        Button updatestation = findViewById(R.id.button5);
        TextView messages = findViewById(R.id.editTextTextPersonName12);
        TextView remainseats = findViewById(R.id.editTextTextPersonName11);
        Spinner spinner = findViewById(R.id.editTextTextPersonName10);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String id = user.getUid();
        DatabaseReference reference = database.getReference().child("userdetails").child(id);

        DatabaseReference references = database.getReference().child("location").child(id);
        DatabaseReference referencess = database.getReference().child("stations").child(id);

        reference.child("noofstation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noofsation = snapshot.getValue(String.class);
                tesi.setText(noofsation);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tesi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                int val = Integer.parseInt(tesi.getText().toString());

                String [] stationslist = new String[]{"Current Station"};
                List<String> stationslists = new ArrayList<String>(Arrays.asList(stationslist));

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,stationslists);
                spinner.setAdapter(arrayAdapter);

                for(int i =1; i<=val; i++)
                {
                    String aas = Integer.toString(i);
                    referencess.child(aas).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            check = snapshot.getValue(String.class);
                            textView.setText(check);

                            String valuess  = textView.getText().toString();
                            stationslists.add(valuess);

                            arrayAdapter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }




            }
        });




        updatestation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int vall = spinner.getSelectedItemPosition();
                String laststation = spinner.getItemAtPosition(vall-1).toString();
                String nextstation = spinner.getItemAtPosition(vall+1).toString();

                database.getReference().child("userdetails").child(id).child("laststation").setValue(laststation);
                database.getReference().child("userdetails").child(id).child("nextstation").setValue(nextstation);















            }
        });


        remainseat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remainss = remainseats.getText().toString();

                reference.child("remainseat").setValue(remainss);
                remainseats.setText("");
            }
        });


        locationbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    startLocationService();
                }
                else{
                    stopLocationService();
                }
            }
        });



        reference.child("BusNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String passwords = snapshot.getValue(String.class);
                vehicleno.setText(passwords);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("UniqueNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String passwords = snapshot.getValue(String.class);
                uniqueno.setText(passwords);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("NoonSeat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String passwords = snapshot.getValue(String.class);
                noofseat.setText(passwords);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("startinglocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String passwords = snapshot.getValue(String.class);
                startinglocation.setText(passwords);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("endinglocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String passwords = snapshot.getValue(String.class);
                endlocation.setText(passwords);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("drivername").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String passwords = snapshot.getValue(String.class);
                drivername.setText(passwords);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("starttime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String passwords = snapshot.getValue(String.class);
                starttime.setText(passwords);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messages.getText().toString();

                reference.child("message").setValue(message);
                messages.setText("");
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                startLocationService();
            }
            else
            {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private  boolean isLocationServiceRunning()
    {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if(activityManager!=null)
        {
            for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE))
            {
                if(LocationService.class.getName().equals(service.service.getClassName()))
                {
                    if(service.foreground)
                    {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void startLocationService()
    {
        if(!isLocationServiceRunning())
        {
            Intent intent  = new Intent(getApplicationContext(),LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location Service Started", Toast.LENGTH_SHORT).show();

        }
    }

    private  void stopLocationService()
    {
        if(isLocationServiceRunning())
        {
            Intent intent = new Intent(getApplicationContext(),LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location Service Stopped", Toast.LENGTH_SHORT).show();
        }
    }
}
