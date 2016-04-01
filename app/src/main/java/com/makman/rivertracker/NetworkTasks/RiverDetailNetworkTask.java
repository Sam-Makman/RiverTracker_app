package com.makman.rivertracker.NetworkTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.makman.rivertracker.River;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by prog on 3/29/16.
 */
public class RiverDetailNetworkTask extends AsyncTask<String, Boolean, River> {

    private static final String riverURL="https://radiant-temple-90497.herokuapp.com/api/river?id=";
    private static final String TAG = RiverDetailNetworkTask.class.getSimpleName();
    RiverDetailNetworkTaskListener mListener;

    public RiverDetailNetworkTask(RiverDetailNetworkTaskListener listener) {
        mListener=listener;
    }

    public interface RiverDetailNetworkTaskListener{
        void PostExecute(River river);
    }

    @Override
    protected River doInBackground(String... strings) {
        StringBuilder responseBuilder = new StringBuilder();
        River river = null;
        if(strings.length == 0){ return null; }

        String id = strings[0];

        try {
            URL url = new URL(riverURL+id);
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

            river = new Gson().fromJson(responseBuilder.toString(), River.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "doInBackground: great success");
        return river;

    }

    @Override
    protected void onPostExecute(River river) {
        super.onPostExecute(river);
        mListener.PostExecute(river);
    }

}
