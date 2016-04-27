package com.makman.rivertracker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.makman.rivertracker.Activities.LoginActivity;
import com.makman.rivertracker.FavoritesActivity;
import com.makman.rivertracker.NetworkTasks.MultiRiverNetworkTask;
import com.makman.rivertracker.R;
import com.makman.rivertracker.River;
import com.makman.rivertracker.RiverMapsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchFragment extends Fragment implements OnClickListener, MultiRiverNetworkTask.MultiRiverNetworkTaskListener {

    private static final String TAG = SearchFragment.class.getSimpleName();
    public static final String BACKGROUND_URL = "http://res.cloudinary.com/hgsa3o7eg/image/upload/v1460683974/12400671_863883430404267_1928887859281497838_n_wxenjo.png";

    @Bind(R.id.search_spinner_difficulty)
    public Spinner mDifficultySpinner;

    @Bind(R.id.search_button)
    public Button mButton;

    @Bind(R.id.search_edit_river_name)
    public EditText mNameEdit;

    @Bind(R.id.search_edit_river_section)
    public EditText mSectionEdit;

    @Bind(R.id.search_spinner_state)
    public Spinner mStateSpinner;
    @Bind(R.id.search_background_image)
    public ImageView mBackground;

    @Bind(R.id.search_map_button)
    public Button mMapButton;

    @Bind(R.id.search_progress_bar)
    public ProgressBar mProgress;
    private boolean useMap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, rootView);
        useMap = false;
        mButton.setOnClickListener(this);
        mMapButton.setOnClickListener(this);

        Picasso.with(getContext()).load(BACKGROUND_URL).fit().centerCrop().into(mBackground);

        ((FavoritesActivity) getActivity()).setTitle("Search For Rivers");

        mDifficultySpinner = (Spinner) rootView.findViewById(R.id.search_spinner_difficulty);



        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.difficulty_ratings,
                R.layout.spinner_item);

        mDifficultySpinner.setAdapter(spinnerAdapter);


        mStateSpinner = (Spinner) rootView.findViewById(R.id.search_spinner_state);
        ArrayAdapter<CharSequence> stateSpinAdapt = ArrayAdapter.createFromResource(getContext(),
                R.array.states,
                R.layout.spinner_item);

        mStateSpinner.setAdapter(stateSpinAdapt);

        return rootView;
    }


    @OnClick(R.id.search_button)
    void click(){

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == mMapButton.getId()){
            useMap = true;
        }
        MultiRiverNetworkTask task;
        String url = FavoritesActivity.RIVER_URL;
        url = url + "?";
        url += "name=" + mNameEdit.getText().toString() + "&";
        if(!mDifficultySpinner.getSelectedItem().toString().equals("All")){
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
            mProgress.setVisibility(View.VISIBLE);
            task = new MultiRiverNetworkTask(this);
            task.execute(url, getString(R.string.search_results));
        }
    }

    @Override
    public void PostExecute(ArrayList<River> rivers, boolean invalidToken, String title) {
        mProgress.setVisibility(View.GONE);
        if(invalidToken){
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();

        }else if(rivers == null){
            Toast.makeText(getContext(), R.string.cannot_connect, Toast.LENGTH_SHORT).show();
        }else {
            if(useMap){
                Intent intent = new Intent(getContext(), RiverMapsActivity.class);
                intent.putParcelableArrayListExtra(RiversFragment.ARG_RIVERS, rivers);
                startActivity(intent);
            }else{
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                RiversFragment riversFragment = RiversFragment.newInstance(rivers, title);
                transaction.replace(R.id.activity_favorite_frame_layout, riversFragment);
                transaction.commit();
            }

        }
        useMap = false;
    }
}
