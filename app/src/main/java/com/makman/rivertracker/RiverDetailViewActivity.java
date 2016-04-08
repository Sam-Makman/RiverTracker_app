package com.makman.rivertracker;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makman.rivertracker.Fragments.RiversFragment;
import com.makman.rivertracker.NetworkTasks.RiverDetailNetworkTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RiverDetailViewActivity extends AppCompatActivity implements RiverDetailNetworkTask.RiverDetailNetworkTaskListener {
    private static final String TAG = RiverDetailViewActivity.class.getSimpleName();
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
        //call an async task
        //send api token and river id
    }


    private RiverDetailNetworkTask mNetworkTask;
    private River river;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_detail_view);
        ButterKnife.bind(this);
        river = getIntent().getExtras().getParcelable(RiversFragment.ARG_RIVER);
        if(river!=null){
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if(networkInfo==null || !networkInfo.isConnected()){
                Toast.makeText(this, "can't load details", Toast.LENGTH_SHORT).show();
            }else{
                String riverId = river.getId();
                mNetworkTask = new RiverDetailNetworkTask(this);
                mNetworkTask.execute(riverId);
            }
        }

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
