package com.makman.rivertracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makman.rivertracker.FavoritesActivity;
import com.makman.rivertracker.Fragments.RiversFragment;
import com.makman.rivertracker.NetworkTasks.MultiRiverNetworkTask;
import com.makman.rivertracker.NetworkTasks.RiverDetailNetworkTask;
import com.makman.rivertracker.R;
import com.makman.rivertracker.River;
import com.makman.rivertracker.RiverDetailViewActivity;
import com.makman.rivertracker.RiverMapsActivity;
import com.makman.rivertracker.RiverRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RiversActivity extends AppCompatActivity implements RiverRecyclerViewAdapter.OnRiverRowClickListener, MultiRiverNetworkTask.MultiRiverNetworkTaskListener, View.OnClickListener{


    public static final String ARG_RIVERS = "arg_rivers";
    public static final String ARG_RIVER="arg_river";
    public static final String RIVER_URL = "https://radiant-temple-90497.herokuapp.com/api/rivers";
    public static final String FAVORITE_URL = "https://radiant-temple-90497.herokuapp.com/api/favorites?token=";
    public static final String FAVORITES = "favorites";
    private static final String TAG = RiversFragment.class.getSimpleName();
    private static final String ARG_TITLE = "arg_title";

    public ArrayList<River> mRivers;
    private SharedPreferences mPreferences;

    @Bind(R.id.activity_rivers_recycler_view)
    RecyclerView mRecyclerView;

    RiverRecyclerViewAdapter mAdapter;

    @Bind(R.id.activity_rivers_no_results)
    TextView mNoResults;

    @Bind(R.id.activity_rivers_fab)
    FloatingActionButton fab;
    private MultiRiverNetworkTask mTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

//        mProgress.setVisibility(View.VISIBLE);
        mPreferences = getSharedPreferences(LoginActivity.PREFERENCES, Context.MODE_PRIVATE);

        String token = mPreferences.getString(LoginActivity.TOKEN, "");
        String url;

        Log.d(TAG, "token = " + token);
        if(token.equals("")){
            getRivers(RIVER_URL, getString(R.string.all_rivers));
        }else{
            url = FAVORITE_URL + token;
            getRivers(url, getString(R.string.favorites));
            Log.d(TAG, url);
        }

        fab.setOnClickListener(this);
//        mRivers = getIntent().getParcelableArrayListExtra(ARG_RIVERS);
        if(mRivers != null && mRivers.size() > 0){
            mNoResults.setVisibility(View.GONE);
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            mAdapter = new RiverRecyclerViewAdapter(mRivers, this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }else{

            mRecyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void PostExecute(ArrayList<River> rivers, boolean invalidToken, String title) {
//        mProgress.setVisibility(View.GONE);
        if(invalidToken){
            Toast.makeText(this, R.string.token_logout_error, Toast.LENGTH_SHORT).show();
            logout();
        }else if(rivers==null){
            Toast.makeText(this, R.string.cannot_connect, Toast.LENGTH_SHORT).show();
        }else {
            RiversFragment riversFragment = RiversFragment.newInstance(rivers, title);

            SharedPreferences preferences = getSharedPreferences(LoginActivity.PREFERENCES,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            String token = mPreferences.getString(LoginActivity.TOKEN, "");
            if(title.equals(getString(R.string.favorites))) {
                String riverID = "";
                for (River r : rivers) {
                    riverID += r.getId() + ",";
                }
                editor.putString(FAVORITES, riverID);
                editor.commit();
                Log.d(TAG, riverID);
            }

            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_favorite_frame_layout, riversFragment);
            transaction.addToBackStack("favorites");
            transaction.commit();
        }
    }

    @Override
    public void onRiverRowClicked(River river) {
        if(river!=null) {
            Intent intent = new Intent(this, RiverDetailViewActivity.class);
            intent.putExtra(ARG_RIVER, river);
            startActivity(intent);
        }
    }


    private void logout(){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(LoginActivity.TOKEN, "");
        editor.apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, RiverMapsActivity.class);
        intent.putExtra(ARG_RIVERS, mRivers);
        startActivity(intent);
    }

    public void getRivers(String url, String title){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo network = manager.getActiveNetworkInfo();
        if(network == null || !network.isConnected()){
            Toast.makeText(this, R.string.cannot_connect, Toast.LENGTH_SHORT).show();
        }else{
            mTask = new MultiRiverNetworkTask(this);
            mTask.execute(url, title);
        }
    }

}
