package com.makman.rivertracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.makman.rivertracker.Activities.LoginActivity;
import com.makman.rivertracker.Fragments.MapFragment;
import com.makman.rivertracker.Fragments.RiversFragment;
import com.makman.rivertracker.NetworkTasks.RiverDetailNetworkTask;
import com.makman.rivertracker.NetworkTasks.VolleyNetworkTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RiverDetailViewActivity extends AppCompatActivity implements RiverDetailNetworkTask.RiverDetailNetworkTaskListener, Response.ErrorListener, Response.Listener<JSONObject> {
    private static final String TAG = RiverDetailViewActivity.class.getSimpleName();
    private static final String favoriteURL = "https://radiant-temple-90497.herokuapp.com/api/favorite?id=";
    private static final String alertURL = "https://radiant-temple-90497.herokuapp.com/api/alert?id=";
    public static final String PREFERENCES = "TOKEN_PREFERENCES";
    public static final String RIVERMAP = "river_map";
    SharedPreferences mPreference;

    @Bind(R.id.river_details_alerts_textview)
    TextView mAlertsTextview;

    @Bind(R.id.river_details_image_view)
    ImageView mRiverImage;

    @Bind(R.id.river_details_section_textview)
    TextView mRiverSection;

    @Bind(R.id.river_details_difficulty_textview)
    TextView mDifficulty;

    @Bind(R.id.river_details_state_textview)
    TextView mState;

    @Bind(R.id.river_details_flowrate_textview)
    TextView mFlowrate;
    
    @Bind(R.id.river_details_description_textview)
    TextView mRiverDescription;

    @OnClick(R.id.river_details_favorite_button) void OnClick(){
        String url = favoriteURL + river.getId() + "&token=" + mPreference.getString(LoginActivity.TOKEN, "");
        Log.d(TAG, "OnClick: " + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleyNetworkTask.getInstance().getRequestQueue().add(jsonObjectRequest);
    }

    private River river;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_detail_view);
        ButterKnife.bind(this);
        mPreference = getSharedPreferences(RiverDetailViewActivity.PREFERENCES,Context.MODE_PRIVATE);
        river = getIntent().getParcelableExtra(RiversFragment.ARG_RIVER);
        getSupportActionBar().setTitle(river.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RIVERMAP, river);
        MapFragment map = new MapFragment();
        map.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.river_details_map_frame, map);
        transaction.commit();
        PostExecute(river);
    }

    @Override
    public void PostExecute(River river) {
        String imageURL = river.getPicture().getPicture().getUrl();
        if(river==null){
            return;
        }
        if(river.getSection()!=null) {
            mRiverSection.setText(river.getSection());
        }
        if(river.getDifficulty()!=null) {
            mDifficulty.setText(river.getDifficulty());
        }
        if(river.getState()!=null) {
            mState.setText(river.getState());
        }
        if(river.getCfs()!=null) {
            mFlowrate.setText(river.getCfs());
        }
        if(river.getDetails()!=null) {
            mRiverDescription.setText(river.getDetails().toString());
        }
        if(imageURL != null){
            Picasso.with(this).load(imageURL).fit().centerCrop().into(mRiverImage);
        }
        String realAlertURL = alertURL + river.getId();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, realAlertURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Alert[] alerts = new Alert[response.length()];
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject=(JSONObject) response.get(i);
                        alerts[i]=new Alert(jsonObject.getString("title"), jsonObject.getString("details"), jsonObject.getString("updated_at"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setAlerts(alerts);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        VolleyNetworkTask.getInstance().getRequestQueue().add(jsonArrayRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d(TAG, "hit the onResponse");
        Toast.makeText(this, R.string.river_favorited_toast, Toast.LENGTH_SHORT).show();
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

    public void setAlerts(Alert[] alerts){
        String alertString = "";
        for(Alert alert: alerts){
            alertString+=alert.getmTitle() + "\n\n";
            alertString+=alert.getmDescription() + "\n";
            alertString+=alert.getmDate() + "\n\n\n";
        }

        mAlertsTextview.setText(alertString);

    }
}
