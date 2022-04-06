package com.thehyperprogrammer.notespedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BusMapView extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap Map;
    Marker userMarker,busMarker;
    String college,busKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_map_view);
        Bundle extras = getIntent().getExtras();
        college = extras.getString("college");
        busKey = extras.getString("busKey");
        Mapinit();

        PlaceBusMarker();

    }

    private void PlaceBusMarker() {
        FirebaseDatabase.getInstance().getReference()
                .child("BUS LOCATIONS")
                .child(college)
                .child(busKey)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(busMarker != null){
                            busMarker.remove();
                        }

                        Double latitude,longitude;
                        String bus_number;

                        Bitmap bus_ic = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_bus_ic);
                        Bitmap smallMarker = Bitmap.createScaledBitmap(bus_ic, 100, 100, false);
                        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                        latitude = Double.valueOf(dataSnapshot.child("latitude").getValue().toString());
                        longitude = Double.valueOf(dataSnapshot.child("longitude").getValue().toString());
                        bus_number = dataSnapshot.child("bus_number").getValue().toString();

                        LatLng latLng = new LatLng(latitude,longitude);
                        busMarker = Map.addMarker(new MarkerOptions().position(latLng).title(bus_number)
                                .icon(smallMarkerIcon).anchor(0.5f, 1));
                        busMarker.showInfoWindow();
                        Map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        Map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,25));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Map = googleMap;
        Map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Map.getUiSettings().setZoomGesturesEnabled(true);
        Map.getUiSettings().setMapToolbarEnabled(false);
    }
    private void Mapinit(){
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map_view_id);
        mapFragment.getMapAsync(this);
    }

}
