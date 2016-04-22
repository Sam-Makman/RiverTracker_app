package com.makman.rivertracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makman.rivertracker.R;
import com.makman.rivertracker.River;
import com.makman.rivertracker.RiverDetailViewActivity;

import butterknife.ButterKnife;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = MapFragment.class.getSimpleName();
    private SupportMapFragment mSupportMapFragment;
    private River mRiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment_details, container, false);
        ButterKnife.bind(this, rootView);
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment_for_detail_view);
        mSupportMapFragment.getMapAsync(this);
        mRiver = getArguments().getParcelable(RiverDetailViewActivity.RIVERMAP);
        Log.d(TAG, "mapfragment oncreateview");
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "mapfragment onmapready");
        String[] putInCoords=mRiver.getPut_in().split(",");
        String[] takeOutCoords=mRiver.getTake_out().split(",");
        LatLng putCoords = new LatLng(Double.valueOf(putInCoords[0]), Double.valueOf(putInCoords[1]));
        LatLng takeCoords = new LatLng(Double.valueOf(takeOutCoords[0]), Double.valueOf(takeOutCoords[1]));
        googleMap.addMarker(new MarkerOptions().position(putCoords).title("Put in"));
        googleMap.addMarker(new MarkerOptions().position(takeCoords).title("Take out"));

        double minLat, maxLat, minLong, maxLong;

        if(Double.valueOf(putInCoords[0]) < Double.valueOf(takeOutCoords[0])){
            minLat = Double.valueOf(putInCoords[0]);
            maxLat = Double.valueOf(takeOutCoords[0]);
        }else{
            maxLat = Double.valueOf(putInCoords[0]);
            minLat = Double.valueOf(takeOutCoords[0]);
        }

        if(Double.valueOf(putInCoords[1]) < Double.valueOf(takeOutCoords[1])){
            minLong = Double.valueOf(putInCoords[1]);
            maxLong = Double.valueOf(takeOutCoords[1]);
        }else{
            maxLong = Double.valueOf(putInCoords[1]);
            minLong = Double.valueOf(takeOutCoords[1]);
        }

        LatLngBounds bounds = new LatLngBounds(new LatLng(minLat, minLong), new LatLng(maxLat, maxLong));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,200 ));

    }
}