package com.makman.rivertracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

    @Bind(R.id.river_detail_description_button)
    Button mDescription;

    @Bind(R.id.river_detail_alert_button)
    Button mAlert;

    @Bind(R.id.river_detail_map_button)
    Button mMap;

    @Bind(R.id.river_details_button_home)
    Button mHome;

    @Bind(R.id.river_details_section)
    TextView mSection;

    @Bind(R.id.river_details_favorite_button)
    Button mFavorite;

    @Bind(R.id.river_details_name)
    TextView mName;

    @OnClick(R.id.river_detail_description_button)
    void onDescriptionClick(){
        mDescription.setTextColor(getResources().getColor(R.color.colorPrimary));
        mMap.setTextColor(Color.parseColor("#000000"));
        mAlert.setTextColor(Color.parseColor("#000000"));
        RiverDescriptionFragment fragment = RiverDescriptionFragment.newInstance(river);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.river_detail_frame_layout, fragment);
        transaction.commit();
    }

    @OnClick(R.id.river_detail_alert_button)
    void onAlertClick(){
        mDescription.setTextColor(Color.parseColor("#000000"));
        mMap.setTextColor(Color.parseColor("#000000"));
        mAlert.setTextColor(getResources().getColor(R.color.colorPrimary));
        RiverAlertFragment fragment = new RiverAlertFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.river_detail_frame_layout, fragment);
        transaction.commit();
    }

    @OnClick(R.id.river_detail_map_button)
    void onMapClick(){
        mDescription.setTextColor(Color.parseColor("#000000"));
        mMap.setTextColor(getResources().getColor(R.color.colorPrimary));
        mAlert.setTextColor(Color.parseColor("#000000"));
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RiverDetailViewActivity.DETAILRIVER, river);
        mapFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.river_detail_frame_layout, mapFragment);
        transaction.commit();
    }

    @OnClick(R.id.river_details_favorite_button)
    void onFavoriteClick(){
        String url = favoriteURL + river.getId() + "&token=" + mPreference.getString(LoginActivity.TOKEN, "");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleyNetworkTask.getInstance().getRequestQueue().add(jsonObjectRequest);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mFavorite.setBackground(getDrawable(R.drawable.ic_star_favorite));
        }else{
            mFavorite.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_star_favorite));
        }
    }

    @OnClick(R.id.river_details_button_home)
    void homeClick(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_detail_view);
        ButterKnife.bind(this);
        mPreference = getSharedPreferences(RiverDetailViewActivity.PREFERENCES, Context.MODE_PRIVATE);
        river = getIntent().getParcelableExtra(RiversFragment.ARG_RIVER);

        mSection.setText(river.getSection());
        mName.setText(river.getName());
        ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.setTitle(river.getName());
            bar.hide();

        }
        RiverDescriptionFragment fragment = RiverDescriptionFragment.newInstance(river);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.river_detail_frame_layout, fragment);
        transaction.commit();
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

    public void setAlerts(Alert[] alerts) {
        String alertString = "";
        for (Alert alert : alerts) {
            alertString += alert.getmTitle() + "\n\n";
            alertString += alert.getmDescription() + "\n";
            alertString += alert.getmDate() + "\n\n\n";
        }
    }
}
