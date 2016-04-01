package com.makman.rivertracker;

import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makman.rivertracker.Fragments.RiversFragment;
import com.makman.rivertracker.NetworkTasks.RiverDetailNetworkTask;

import org.w3c.dom.Text;

public class RiverDetailViewActivity extends AppCompatActivity implements RiverDetailNetworkTask.RiverDetailNetworkTaskListener {
    private static final String TAG = RiverDetailViewActivity.class.getSimpleName();
    private TextView mAlertsTextview;
    private ImageView mRiverImage;
    private TextView mRiverSection;
    private TextView mDifficulty;
    private TextView mState;
    private TextView mFlowrate;
    private TextView mRiverDescription;
    private RiverDetailNetworkTask mNetworkTask;
    private River river;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_detail_view);
        mAlertsTextview = (TextView) findViewById(R.id.river_details_alerts_textview);
        mRiverImage = (ImageView) findViewById(R.id.river_details_image_view);
        mRiverSection = (TextView) findViewById(R.id.river_details_section_textview);
        mDifficulty = (TextView) findViewById(R.id.river_details_difficulty_textview);
        mState = (TextView) findViewById(R.id.river_details_state_textview);
        mFlowrate = (TextView) findViewById(R.id.river_details_flowrate_textview);
        mRiverDescription = (TextView) findViewById(R.id.river_details_description_textview);

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
