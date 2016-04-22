package com.makman.rivertracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.makman.rivertracker.Activities.LoginActivity;
import com.makman.rivertracker.Fragments.MapFragment;
import com.makman.rivertracker.Fragments.RiverAlertFragment;
import com.makman.rivertracker.Fragments.RiverDescriptionFragment;
import com.makman.rivertracker.Fragments.RiversFragment;
import com.makman.rivertracker.NetworkTasks.RiverDetailNetworkTask;
import com.makman.rivertracker.NetworkTasks.VolleyNetworkTask;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RiverDetailViewActivity extends AppCompatActivity implements RiverDetailNetworkTask.RiverDetailNetworkTaskListener, Response.ErrorListener, Response.Listener<JSONObject> {
    private static final String TAG = RiverDetailViewActivity.class.getSimpleName();
    private static final String favoriteURL = "https://radiant-temple-90497.herokuapp.com/api/favorite?id=";
    private static final String alertURL = "https://radiant-temple-90497.herokuapp.com/api/alert?id=";
    public static final String PREFERENCES = "TOKEN_PREFERENCES";
    public static final String DETAILRIVER = "detail_river";
    SharedPreferences mPreference;
    Bundle mBundle;
    River river;


    @Bind(R.id.river_details_image_view)
    ImageView mRiverImage;

    @OnClick(R.id.river_detail_description_button)
    void onDescriptionClick(){
        RiverDescriptionFragment fragment = RiverDescriptionFragment.newInstance(river);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.river_detail_frame_layout, fragment);
        transaction.commit();
    }

    @OnClick(R.id.river_detail_alert_button)
    void onAlertClick(){
        RiverAlertFragment fragment = new RiverAlertFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.river_detail_frame_layout, fragment);
        transaction.commit();
    }

    @OnClick(R.id.river_detail_map_button)
    void onMapClick(){
        MapFragment mapFragment = new MapFragment();
    }

    @OnClick(R.id.river_details_favorite_button)
    void onFavoriteClick(){
        String url = favoriteURL + river.getId() + "&token=" + mPreference.getString(LoginActivity.TOKEN, "");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleyNetworkTask.getInstance().getRequestQueue().add(jsonObjectRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_detail_view);
        ButterKnife.bind(this);
        mPreference = getSharedPreferences(RiverDetailViewActivity.PREFERENCES, Context.MODE_PRIVATE);
        river = getIntent().getParcelableExtra(RiversFragment.ARG_RIVER);
        PostExecute(river);
    }

    @Override
    public void PostExecute(River river) {
        String imageURL = river.getPicture().getPicture().getUrl();
        Picasso.with(this).load(imageURL).fit().centerCrop().into(mRiverImage);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(TAG, error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "@string/river_favorited_toast", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setAlerts(Alert[] alerts) {
        String alertString = "";
        for (Alert alert : alerts) {
            alertString += alert.getmTitle() + "\n\n";
            alertString += alert.getmDescription() + "\n";
            alertString += alert.getmDate() + "\n\n\n";
        }
    }
}
