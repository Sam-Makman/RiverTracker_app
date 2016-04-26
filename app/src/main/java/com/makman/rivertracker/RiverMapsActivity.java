package com.makman.rivertracker;

import android.content.Intent;
import android.graphics.Camera;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makman.rivertracker.Fragments.RiversFragment;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RiverMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ArrayList<River> mRivers;
    private River mCurRiver;
    @Bind(R.id.maps_river_detail_bar)
    RelativeLayout mDetailBar;

    @Bind(R.id.maps_river_cfs)
    TextView mCfs;

    @Bind(R.id.maps_river_difficulty)
    TextView mDifficulty;

    @Bind(R.id.maps_river_state)
    TextView mState;

    @Bind(R.id.maps_river_direction_button)
    Button mDirectionButotn;

    @Bind(R.id.maps_river_detail_button)
    Button mDetailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mRivers = getIntent().getParcelableArrayListExtra(RiversFragment.ARG_RIVERS);
        ButterKnife.bind(this);
        mDetailBar.setVisibility(View.GONE);
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
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
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
            }
            LatLngBounds bounds = new LatLngBounds(new LatLng(minLat, minLong), new LatLng(maxLat, maxLong));
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,100));

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        mDetailBar.setVisibility(View.VISIBLE);
        River river = null;
        for( River r : mRivers){
            if(r.getName().equals(marker.getTitle()) && r.getSection().equals(marker.getSnippet())){
                river = r;
            }
        }
        if(river == null){
            return false;
        }
        mCurRiver = river;
        mDifficulty.setText(river.getDifficulty());
        mState.setText(river.getState());
        String cfs;
        if(river.getCfs() == null){
            cfs = "";
        }else {
            cfs = String.format(getString(R.string.cfs), river.getCfs());
        }
        mCfs.setText(cfs);

        return true;
    }

    @OnClick (R.id.maps_river_direction_button)
    void click(){
        Toast.makeText(this, "Open in google maps", Toast.LENGTH_SHORT).show();
        if(mCurRiver == null){
            return;
        }
        String[] coords = mCurRiver.getPut_in().split(",");
        double lat = Double.valueOf(coords[0]);
        double lng = Double.valueOf(coords[1]);
        String uri = String.format(Locale.ENGLISH, "google.navigation:q=%f,%f",lat,lng );
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @OnClick(R.id.maps_river_detail_button)
    void details(){
        Intent intent = new Intent(this,RiverDetailViewActivity.class);
        intent.putExtra(RiversFragment.ARG_RIVER, mCurRiver);
        startActivity(intent);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mDetailBar.setVisibility(View.GONE);
    }
}


