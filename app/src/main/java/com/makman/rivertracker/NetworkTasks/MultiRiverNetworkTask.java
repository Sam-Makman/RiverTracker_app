package com.makman.rivertracker.NetworkTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.makman.rivertracker.River;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class MultiRiverNetworkTask extends AsyncTask<String , Boolean, ArrayList<River>> {

    public interface MultiRiverNetworkTaskListener{
         void PostExecute(ArrayList<River> rivers);
    }

    MultiRiverNetworkTaskListener mListener;

    public MultiRiverNetworkTask(MultiRiverNetworkTaskListener listener) {
        mListener = listener;
    }

    @Override
    protected ArrayList<River> doInBackground(String... params) {
        StringBuilder responseBuilder = new StringBuilder();
        ArrayList<River> rivers = null;
        if(params.length == 0){ return null; }

        String address = params[0];

        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;

            if (isCancelled()) {
                return null;
            }
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);

                if (isCancelled()) {
                    return null;
                }
            }

            River[] riverArray = new Gson().fromJson(responseBuilder.toString(), River[].class);
            rivers = new ArrayList<>();
            rivers.addAll(Arrays.asList(riverArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rivers;
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
