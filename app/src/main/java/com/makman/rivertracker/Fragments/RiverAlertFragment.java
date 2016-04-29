package com.makman.rivertracker.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makman.rivertracker.Alert;
import com.makman.rivertracker.AlertRecyclerViewAdapter;
import com.makman.rivertracker.R;
import com.makman.rivertracker.RiverDetailViewActivity;

import butterknife.ButterKnife;

public class RiverAlertFragment extends Fragment {
    public static final String TAG=RiverAlertFragment.class.getSimpleName();
    //volley to grab array of alerts
    //recyclerview
    //recyclerview adapter
    RecyclerView mRecyclerView;
    AlertRecyclerViewAdapter mAdapter;
    Alert[] mAlerts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_river_description, container, false);
        ButterKnife.bind(this, view);
        mAlerts=(Alert[]) getArguments().getParcelableArray(RiverDetailViewActivity.DETAILRIVER);
        mAdapter=new AlertRecyclerViewAdapter(mAlerts);
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public static RiverAlertFragment newInstance(Alert[] alerts){
        RiverAlertFragment riverAlertFragment = new RiverAlertFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(RiverDetailViewActivity.DETAILRIVER, alerts);
        riverAlertFragment.setArguments(bundle);
        return riverAlertFragment;
    }
}
