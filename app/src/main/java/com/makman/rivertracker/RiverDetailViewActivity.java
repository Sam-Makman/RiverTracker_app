package com.makman.rivertracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.makman.rivertracker.Fragments.RiversFragment;
import com.makman.rivertracker.NetworkTasks.RiverDetailNetworkTask;
import com.makman.rivertracker.NetworkTasks.VolleyNetworkTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RiverDetailViewActivity extends AppCompatActivity implements RiverDetailNetworkTask.RiverDetailNetworkTaskListener {
    private static final String TAG = RiverDetailViewActivity.class.getSimpleName();
    private static final String URL = "https://radiant-temple-90497.herokuapp.com/api/favorite?id={river id}&api_token={api token}";
    public static final String PREFERENCES = "TOKEN_PREFERENCES";
    private SharedPreferences mPreference;

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
        //api token and river id
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("form");
                            String site = jsonResponse.getString("site"),
                                    network = jsonResponse.getString("network");
                            System.out.println("Site: "+site+"\nNetwork: "+network);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("river id", river.getId());
                params.put("api token", mPreference.getString("token", null));
                return params;
            }
        };
        VolleyNetworkTask.getInstance().getRequestQueue().add(postRequest);

    }


    private River river;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_detail_view);
        ButterKnife.bind(this);
        mPreference = getSharedPreferences(RiverDetailViewActivity.PREFERENCES,Context.MODE_PRIVATE);
        river = getIntent().getParcelableExtra(RiversFragment.ARG_RIVER);
        PostExecute(river);
    }

    @Override
    public void PostExecute(River river) {
        Log.d(TAG, "post execute");
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
    }
}
