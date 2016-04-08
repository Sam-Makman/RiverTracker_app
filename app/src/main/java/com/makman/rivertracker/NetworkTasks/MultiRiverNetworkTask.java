package com.makman.rivertracker.NetworkTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.makman.rivertracker.River;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class MultiRiverNetworkTask extends AsyncTask<String , Boolean, ArrayList<River>> {

    private static final String TAG = MultiRiverNetworkTask.class.getSimpleName();

    public interface MultiRiverNetworkTaskListener{
         void PostExecute(ArrayList<River> rivers, boolean invalidToken, String title);
    }

    MultiRiverNetworkTaskListener mListener;
    String mTitle;
    public MultiRiverNetworkTask(MultiRiverNetworkTaskListener listener) {
        mListener = listener;
    }

    @Override
    protected ArrayList<River> doInBackground(String... params) {
        StringBuilder responseBuilder = new StringBuilder();
        ArrayList<River> rivers = null;
        if(params.length == 0){ return null; }
        mTitle = params[1];

        String address = params[0];

        try {
            Log.d(TAG, address);
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
//            JSONObject json = new JSONObject(responseBuilder.toString());
            rivers = new ArrayList<>();
            if(responseBuilder.toString().contains("error")){
                River river = new River();
                river.setId("-1");
                rivers.add(river);
                Log.d(TAG, "JSON ERROR");
                Log.d(TAG, responseBuilder.toString());
            }else {
                River[] riverArray = new Gson().fromJson(responseBuilder.toString(), River[].class);
                rivers.addAll(Arrays.asList(riverArray));
                Log.d(TAG, "Rivers Loaded ");
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        return rivers;
    }


    @Override
    protected void onPostExecute(ArrayList<River> rivers) {
        super.onPostExecute(rivers);
        if(rivers == null){
            mListener.PostExecute(null, true, mTitle);
        }
        else if(rivers.get(0).getId().equals("-1")){
            mListener.PostExecute(null, false, mTitle);
        }else {
            mListener.PostExecute(rivers, false, mTitle);
        }
    }
}
