package com.makman.rivertracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.makman.rivertracker.Activities.LoginActivity;
import com.makman.rivertracker.Activities.SignUpActivity;
import com.makman.rivertracker.Fragments.RiversFragment;
import com.makman.rivertracker.Fragments.SearchFragment;
import com.makman.rivertracker.NetworkTasks.MultiRiverNetworkTask;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity implements MultiRiverNetworkTask.MultiRiverNetworkTaskListener{

    public static final String ARG_RIVERS = "ARG_RIVER";
    public static final String RIVER_URL = "https://radiant-temple-90497.herokuapp.com/api/rivers";
    public static final String FAVORITE_URL = "https://radiant-temple-90497.herokuapp.com/api/favorites?token=";
    public static final String FAVORITES = "favorites";
    private static final String TAG = FavoritesActivity.class.getSimpleName();

    MultiRiverNetworkTask mTask;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    @Bind(R.id.activity_favorite_progress_bar)
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        supportInvalidateOptionsMenu();

        mProgress.setVisibility(View.VISIBLE);
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

    }

    @Override
    public void PostExecute(ArrayList<River> rivers, boolean invalidToken, String title) {
        mProgress.setVisibility(View.GONE);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, Menu.NONE, R.string.search);
        menu.add(Menu.NONE, 1, Menu.NONE, R.string.menu_item_river_list);
        menu.add(Menu.NONE, 2, Menu.NONE, R.string.logout);
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
                return true;
            case 1:
                getRivers(RIVER_URL,getString(R.string.all_rivers2));
                return true;
            case 2:
                logout();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout(){
        mEditor = mPreferences.edit();
        mEditor.putString(LoginActivity.TOKEN, "");
        mEditor.apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
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
