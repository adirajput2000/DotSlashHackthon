package com.adityarajput.bustrackwar;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.adityarajput.bustrackwar.databinding.ActivityMapShowBusBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class map_show_bus extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapShowBusBinding binding;

    String bus_no;
        double long_check ,lat_check;
        String check;
        String noofsation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapShowBusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        TextView speed = findViewById(R.id.textSpeed);
        TextView busnoo = findViewById(R.id.textView44);
        TextView tesi = findViewById(R.id.textView52);
        TextView textView  = findViewById(R.id.textView51);
        TextView vacent_seat = findViewById(R.id.textView45);
        TextView laststationss = findViewById(R.id.textView48);
        TextView nextstations = findViewById(R.id.textView50);
        ListView listView = findViewById(R.id.listviews);

        DatabaseReference reference = database.getReference().child("location").child(id);
        DatabaseReference references = database.getReference().child("userdetails").child(id);
        DatabaseReference referencess = database.getReference().child("stations").child(id);


        references.child("laststation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noofsation = snapshot.getValue(String.class);
                laststationss.setText(noofsation);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        references.child("nextstation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noofsation = snapshot.getValue(String.class);
                nextstations.setText(noofsation);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        references.child("noofstation").addValueEventListener(new ValueEventListener() {
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

                String [] stationslist = new String[]{};
                List<String> stationslists = new ArrayList<String>(Arrays.asList(stationslist));

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,stationslists);
                listView.setAdapter(arrayAdapter);

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


        references.child("UniqueNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bus_no = snapshot.getValue(String.class);
                busnoo.setText(bus_no);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        references.child("remainseat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bus_no = snapshot.getValue(String.class);
                vacent_seat.setText(bus_no);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("speed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String passwords = Long.toString(snapshot.getValue(Long.class)*4);
                speed.setText(passwords);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("lat_value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lat_check = snapshot.getValue(double.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("long_value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long_check = snapshot.getValue(double.class);
                LatLng sydney = new LatLng(lat_check, long_check);
                mMap.clear();
                int height = 80;
                int width = 80;
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.busicon);
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                mMap.addMarker(new MarkerOptions().position(sydney).title(bus_no).icon(smallMarkerIcon));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat_check, long_check), 18.0f));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
}