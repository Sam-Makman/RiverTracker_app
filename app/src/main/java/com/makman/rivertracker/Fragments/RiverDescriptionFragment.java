package com.makman.rivertracker.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makman.rivertracker.R;
import com.makman.rivertracker.River;
import com.makman.rivertracker.RiverDetailViewActivity;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RiverDescriptionFragment extends Fragment{

    @Bind(R.id.river_detail_description_body)
    TextView mDescription;

    @Bind(R.id.river_detail_cfs)
    TextView mCfs;

    @Bind(R.id.river_detail_difficulty)
    TextView mDifficulty;

    @Bind(R.id.river_detail_state)
    TextView mState;

    @Bind(R.id.river_detail_max_cfs)
            TextView mMaxCfs;

    @Bind(R.id.river_detail_min_cfs)
    TextView mMinCfs;


    River mRiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_river_description, container, false);
        ButterKnife.bind(this, view);
        mRiver=getArguments().getParcelable(RiverDetailViewActivity.DETAILRIVER);
        if(mRiver != null) {
            mCfs.setText(String.format(getContext().getString(R.string.cfs),mRiver.getCfs()));
            mState.setText(mRiver.getState());
            mDifficulty.setText(mRiver.getDifficulty());
            mDescription.setText(mRiver.getDetails().toString());
            if(mRiver.getMax_cfs() == 0){
                mMaxCfs.setVisibility(View.GONE);
            }else{
                mMaxCfs.setText(String.format(getContext().getString(R.string.max_cfs), new Integer(mRiver.getMax_cfs())));

            }
            if(mRiver.getMin_cfs() == 0){
                mMinCfs.setVisibility(View.GONE);
            }else {
                mMinCfs.setText(String.format(getContext().getString(R.string.min_cfs), new Integer(mRiver.getMin_cfs())));
            }
        }
        return view;
    }

    public static RiverDescriptionFragment newInstance(River river){
        RiverDescriptionFragment riverDescriptionFragment = new RiverDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RiverDetailViewActivity.DETAILRIVER, river);
        riverDescriptionFragment.setArguments(bundle);
        return riverDescriptionFragment;
    }
}
