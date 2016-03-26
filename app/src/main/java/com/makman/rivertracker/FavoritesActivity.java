package com.makman.rivertracker;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements MultiRiverNetworkTask.MultiRiverNetworkTaskListener, RiverRecyclerViewAdapter.OnRiverRowClickListener{

    public static final String RIVER_URL = "https://radiant-temple-90497.herokuapp.com/rivers.json";

    RecyclerView mRecyclerView;
    ArrayList<River> mRivers;
    RiverRecyclerViewAdapter mAdapter;
    MultiRiverNetworkTask mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        if(mRivers == null){

            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo network = manager.getActiveNetworkInfo();
            if(network == null || !network.isConnected()){
                Toast.makeText(this, "Cannot Connect", Toast.LENGTH_SHORT).show();
            }else{
                mTask = new MultiRiverNetworkTask(this);
                mTask.execute(RIVER_URL);
            }
        }

    }

    @Override
    public void PostExecute(ArrayList<River> rivers) {
        mRivers = rivers;
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_favorite_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RiverRecyclerViewAdapter(rivers, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onRiverRowClicked(River river) {
        Toast.makeText(this,"Tyler Put in link to the details activity here",Toast.LENGTH_SHORT).show();
    }
}
