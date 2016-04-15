package com.makman.rivertracker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.makman.rivertracker.Activities.LoginActivity;
import com.makman.rivertracker.FavoritesActivity;
import com.makman.rivertracker.NetworkTasks.MultiRiverNetworkTask;
import com.makman.rivertracker.R;
import com.makman.rivertracker.River;

import java.util.ArrayList;


public class SearchFragment extends Fragment implements OnClickListener, MultiRiverNetworkTask.MultiRiverNetworkTaskListener {

    private static final String TAG = SearchFragment.class.getSimpleName();
    public Spinner mDifficultySpinner;
    public Button mButton;
    public EditText mNameEdit;
    public EditText mSectionEdit;
    public Spinner mStateSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        mButton = (Button) rootView.findViewById(R.id.search_button);
        mButton.setOnClickListener(this);
        mNameEdit = (EditText) rootView.findViewById(R.id.search_edit_river_name);
        mSectionEdit = (EditText) rootView.findViewById(R.id.search_edit_river_section);

        ((FavoritesActivity) getActivity()).setTitle("Search For Rivers");

        mDifficultySpinner = (Spinner) rootView.findViewById(R.id.search_spinner_difficulty);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.difficulty_ratings,
                android.R.layout.simple_spinner_dropdown_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDifficultySpinner.setAdapter(spinnerAdapter);


        mStateSpinner = (Spinner) rootView.findViewById(R.id.search_spinner_state);
        ArrayAdapter<CharSequence> stateSpinAdapt = ArrayAdapter.createFromResource(getContext(),
                R.array.states,
                R.layout.support_simple_spinner_dropdown_item);

        mStateSpinner.setAdapter(stateSpinAdapt);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        MultiRiverNetworkTask task;
        String url = FavoritesActivity.RIVER_URL;
        url = url + "?";
        url += "name=" + mNameEdit.getText().toString() + "&";
        if(!mDifficultySpinner.getSelectedItem().toString().equals("None")){
            url += "difficulty=" + mDifficultySpinner.getSelectedItem().toString()+"&";
        }
        if(!mStateSpinner.getSelectedItem().toString().equals("All")){
            url += "state=" + mStateSpinner.getSelectedItem().toString()+"&";
        }
        url += "section=" + mSectionEdit.getText().toString() + "&";
        url += "commit=Search";
        Log.d(TAG, url);
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = manager.getActiveNetworkInfo();
        if(network == null || !network.isConnected()){
            Toast.makeText(getContext(), R.string.cannot_connect, Toast.LENGTH_SHORT).show();
        }else {
            task = new MultiRiverNetworkTask(this);
            task.execute(url, getString(R.string.search_results));
        }
    }

    @Override
    public void PostExecute(ArrayList<River> rivers, boolean invalidToken, String title) {
        if(invalidToken){
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();

        }else if(rivers == null){
            Toast.makeText(getContext(), R.string.cannot_connect, Toast.LENGTH_SHORT).show();
        }else {
            android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            RiversFragment riversFragment = RiversFragment.newInstance(rivers, title);
            transaction.replace(R.id.activity_favorite_frame_layout, riversFragment);
            transaction.commit();
        }
    }
}
