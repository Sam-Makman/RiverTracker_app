package com.makman.rivertracker.Fragments;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.makman.rivertracker.FavoritesActivity;
import com.makman.rivertracker.NetworkTasks.MultiRiverNetworkTask;
import com.makman.rivertracker.NetworkTasks.RiverDetailNetworkTask;
import com.makman.rivertracker.R;
import com.makman.rivertracker.River;
import com.makman.rivertracker.RiverDetailViewActivity;
import com.makman.rivertracker.RiverMapsActivity;
import com.makman.rivertracker.RiverRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class RiversFragment extends Fragment implements RiverRecyclerViewAdapter.OnRiverRowClickListener, RiverDetailNetworkTask.RiverDetailNetworkTaskListener, View.OnClickListener {


    public static final String ARG_RIVERS = "arg_rivers";
    public static final String ARG_RIVER="arg_river";
    private static final String TAG = RiversFragment.class.getSimpleName();
    private static final String ARG_TITLE = "arg_title";

    public ArrayList<River> mRivers;

    RecyclerView mRecyclerView;
    RiverRecyclerViewAdapter mAdapter;
    RiverDetailNetworkTask mDetailTask;
    TextView mNoResults;
    FloatingActionButton fab;
    private String mTitle;
    boolean resumeFlag = false;

    public static RiversFragment newInstance(ArrayList<River> rivers, String title) {

        RiversFragment fragment = new RiversFragment();
        Bundle args = new Bundle();

        River[] riverArray = new River[rivers.size()];

        for(int i = 0;i<rivers.size();i++){
            riverArray[i] = rivers.get(i);
        }
        args.putParcelableArray(ARG_RIVERS, riverArray);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_rivers, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_river_recycler_view);
        mNoResults = (TextView) rootView.findViewById(R.id.fragment_river_no_results);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fragment_river_fab);
        fab.setOnClickListener(this);

        if (getArguments() != null) {
            River[] rivers = (River[]) getArguments().getParcelableArray(ARG_RIVERS);
            if(rivers != null && rivers.length > 0) {
                mRivers = new ArrayList<>(Arrays.asList(rivers));
                mNoResults.setVisibility(View.GONE);
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                mAdapter = new RiverRecyclerViewAdapter(mRivers, this);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            }else{
                mRecyclerView.setVisibility(View.GONE);
            }
            mTitle = getArguments().getString(ARG_TITLE, "");

            if(!mTitle.equals("")){
                ((FavoritesActivity) getActivity()).setTitle(mTitle);
            }
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mTitle.equals(getString(R.string.favorites)) && resumeFlag==true){
            Intent intent = new Intent(getContext(), FavoritesActivity.class);
            startActivity(intent);
            getActivity().finish();
        }else{
            resumeFlag = true;
        }
    }

    @Override
    public void onRiverRowClicked(River river) {
        if(river!=null){
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if(networkInfo==null || !networkInfo.isConnected()){
                Toast.makeText(getActivity(), "can't load details", Toast.LENGTH_SHORT).show();
            }else{
                String riverId = river.getId();
                mDetailTask = new RiverDetailNetworkTask(this);
                mDetailTask.execute(riverId);
            }
        }

    }

    @Override
    public void PostExecute(River river) {
        Intent intent = new Intent(getContext(), RiverDetailViewActivity.class);
        intent.putExtra(ARG_RIVER, river);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), RiverMapsActivity.class);
        intent.putExtra(ARG_RIVERS, mRivers);
        startActivity(intent);
    }

}
