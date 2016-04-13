package com.makman.rivertracker;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makman.rivertracker.Fragments.RiversFragment;

import java.util.ArrayList;

public class RiverMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<River> mRivers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mRivers = getIntent().getParcelableArrayListExtra(RiversFragment.ARG_RIVERS);
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
        boolean search = true;
        double minLat, minLong, maxLat, maxLong;
        if(mRivers != null && mRivers.size() > 0) {
            minLat = 0;
            maxLat = 0;
            minLong = 0;
            maxLong = 0;


            for (River river : mRivers) {
                if (river.getPut_in() != null && !river.getPut_in().equals("")) {
                    String[] coords = river.getPut_in().split(",");
                    double lat = Double.valueOf(coords[0]);
                    double lng = Double.valueOf(coords[1]);
                    if(search){
                        minLat =lat;
                        maxLat = lng;
                        minLong = lat;
                        maxLong = lng;
                        search = false;
                    }
                    if(lat < minLat){
                        minLat = lat;
                    }else if(lat > maxLat){
                        maxLat = lat;
                    }
                    if(lng < minLong){
                        minLong = lng;
                    }else if(lng > maxLong){
                        maxLong = lng;
                    }

                    LatLng place = new LatLng(lat ,lng);
                    mMap.addMarker(
                            new MarkerOptions().position(place).title(river.getName())
                                .snippet(river.getSection())
                    );

                }
                LatLngBounds bounds = new LatLngBounds(new LatLng(minLat, minLong), new LatLng(maxLat, maxLong));
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,100 ));


            }
        }
    }
}
