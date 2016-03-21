package com.makman.rivertracker;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by sam on 3/21/16.
 */
public class MultiRiverNetworkTask extends AsyncTask<String , Boolean, ArrayList<River>> {

    interface MultiRiverNetworkTaskListener{
        public void PostExecute(ArrayList<River> rivers);
    }

    MultiRiverNetworkTaskListener mListener;

    public MultiRiverNetworkTask(MultiRiverNetworkTaskListener listener) {
        mListener = listener;
    }

    @Override
    protected ArrayList<River> doInBackground(String... params) {
        String url = params[0];

        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<River> rivers) {
        super.onPostExecute(rivers);
        mListener.PostExecute(rivers);
    }
}
