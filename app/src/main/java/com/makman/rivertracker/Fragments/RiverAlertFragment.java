package com.makman.rivertracker.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.makman.rivertracker.Alert;
import com.makman.rivertracker.AlertRecyclerViewAdapter;
import com.makman.rivertracker.NetworkTasks.VolleyNetworkTask;
import com.makman.rivertracker.R;
import com.makman.rivertracker.River;
import com.makman.rivertracker.RiverDetailViewActivity;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RiverAlertFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONArray> {
    public static final String TAG=RiverAlertFragment.class.getSimpleName();
    //volley to grab array of alerts
    //recyclerview
    //recyclerview adapter
    @Bind(R.id.river_details_alerts_recyclerview)
    RecyclerView mRecyclerView;
    AlertRecyclerViewAdapter mAdapter;
    River river;
    Alert[] mAlerts;
    private static final String alertURL = "https://radiant-temple-90497.herokuapp.com/api/alert?id=";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_river_alert, container, false);
        ButterKnife.bind(this, view);
        mAdapter=new AlertRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        river = getArguments().getParcelable(RiverDetailViewActivity.DETAILRIVER);
        String finalURL = alertURL + river.getId();
        Log.d(TAG, finalURL);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET ,finalURL, null, this, this);
        VolleyNetworkTask.getInstance().getRequestQueue().add(jsonArrayRequest);
        return view;
    }

    public static RiverAlertFragment newInstance(River river){
        RiverAlertFragment riverAlertFragment = new RiverAlertFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RiverDetailViewActivity.DETAILRIVER, river);
        riverAlertFragment.setArguments(bundle);
        return riverAlertFragment;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onResponse(JSONArray response) {
        mAlerts = new Gson().fromJson(response.toString(), Alert[].class);
        Log.d(TAG, response.toString());
        mAdapter.setmAlerts(mAlerts);
    }
}
