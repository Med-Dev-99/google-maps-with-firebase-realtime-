package com.example.addmarkertomaps;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback ,
        GoogleMap.OnMarkerClickListener{

    /////
    private GoogleMap mMap;
    SearchView sr_location;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //////



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      firebaseDatabase=FirebaseDatabase.getInstance();
      databaseReference=FirebaseDatabase.getInstance().getReference("gps info");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_maps);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
        sr_location=findViewById(R.id.sr_location);
        sr_location.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location =sr_location.getQuery().toString();
                List <Address> address_list=null;
                if (location!=null || !location.equals("")){

                    Geocoder gc= new Geocoder(MainActivity.this);
                    try {
                        address_list=gc.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address =address_list.get(0);
                    LatLng ltl=new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(ltl).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ltl,10));

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

       Marker marker ;
       databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
             for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                 Model model=dataSnapshot.getValue(Model.class);

                 LatLng latLng=new LatLng(model.getLatitude(),model.getLongtitude());
                 MarkerOptions markerOptions= new MarkerOptions();
                 markerOptions.position(latLng);
                 Marker marker = mMap.addMarker(markerOptions);

             }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });




    }
    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Toast.makeText(this,marker.getTitle(),Toast.LENGTH_SHORT).show();
        return false;
    }

}




