package com.makman.rivertracker;

import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.makman.rivertracker.Fragments.RiversFragment;
import com.makman.rivertracker.Fragments.SearchFragment;
import com.makman.rivertracker.NetworkTasks.MultiRiverNetworkTask;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements MultiRiverNetworkTask.MultiRiverNetworkTaskListener{

    public static final String RIVER_URL = "https://radiant-temple-90497.herokuapp.com/rivers.json";

    ArrayList<River> mRivers;
    MultiRiverNetworkTask mTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        supportInvalidateOptionsMenu();


        if(mRivers == null){

            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo network = manager.getActiveNetworkInfo();
            if(network == null || !network.isConnected()){
                Toast.makeText(this, R.string.cannot_connect, Toast.LENGTH_SHORT).show();
            }else{
                mTask = new MultiRiverNetworkTask(this);
                mTask.execute(RIVER_URL);
            }
        }

    }

    @Override
    public void PostExecute(ArrayList<River> rivers) {
        if(rivers==null){
            Toast.makeText(this, R.string.cannot_connect, Toast.LENGTH_SHORT).show();
            return;
        }
        RiversFragment riversFragment = RiversFragment.newInstance(rivers);
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_favorite_frame_layout, riversFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,0,Menu.NONE,"Search");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case 0:
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_favorite_frame_layout, new SearchFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
