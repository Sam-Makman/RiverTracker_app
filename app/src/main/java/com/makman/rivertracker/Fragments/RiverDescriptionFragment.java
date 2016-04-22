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

import butterknife.Bind;
import butterknife.ButterKnife;

public class RiverDescriptionFragment extends Fragment{

    @Bind(R.id.river_detail_description_body)
    TextView mDescription;

    River mRiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_river_description, container, false);
        ButterKnife.bind(this, view);
        mRiver=getArguments().getParcelable(RiverDetailViewActivity.DETAILRIVER);
        mDescription.setText(mRiver.getDetails().toString());
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
