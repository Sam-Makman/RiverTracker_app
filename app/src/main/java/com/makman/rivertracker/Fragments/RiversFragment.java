package com.makman.rivertracker.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.makman.rivertracker.NetworkTasks.MultiRiverNetworkTask;
import com.makman.rivertracker.R;
import com.makman.rivertracker.River;
import com.makman.rivertracker.RiverRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class RiversFragment extends Fragment implements RiverRecyclerViewAdapter.OnRiverRowClickListener {


    public static final String ARG_RIVERS = "arg_rivers";
    private static final String TAG = RiversFragment.class.getSimpleName();

    public ArrayList<River> mRivers;

    RecyclerView mRecyclerView;
    RiverRecyclerViewAdapter mAdapter;
    MultiRiverNetworkTask mTask;


    public static RiversFragment newInstance(ArrayList<River> rivers) {

        RiversFragment fragment = new RiversFragment();
        Bundle args = new Bundle();

        River[] riverArray = new River[rivers.size()];

        for(int i = 0;i<rivers.size();i++){
            riverArray[i] = rivers.get(i);
        }
        args.putParcelableArray(ARG_RIVERS, riverArray);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_rivers, container, false);

        if (getArguments() != null) {
            River[] rivers = (River[]) getArguments().getParcelableArray(ARG_RIVERS);
            if(rivers != null) {
                mRivers = new ArrayList<>(Arrays.asList(rivers));
            }
        }

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_river_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RiverRecyclerViewAdapter(mRivers, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    @Override
    public void onRiverRowClicked(River river) {
        Toast.makeText(getContext(), "REPLACE THIS WITH ACTIVITY CHANGE", Toast.LENGTH_SHORT).show();
    }


}
