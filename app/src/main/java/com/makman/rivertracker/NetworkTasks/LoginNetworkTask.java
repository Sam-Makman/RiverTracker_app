package com.makman.rivertracker.NetworkTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.makman.rivertracker.River;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sam on 3/30/16.
 */
public class LoginNetworkTask extends AsyncTask<String, Integer, String > {
    public static final String URL = "https://radiant-temple-90497.herokuapp.com/api/login?email=";
    private String mEmail;
    private String mPassword;
    private LoginListener mListener;

    public interface LoginListener{
        void onLoginComplete(String token);
    }

    public LoginNetworkTask(String email, String password, LoginListener listener) {
        mEmail = email;
        mPassword = password;
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if(mEmail == null || mPassword == null){
            return null;
        }

        String address = URL + mEmail + "&password=" + mPassword;
        StringBuilder responseBuilder = new StringBuilder();
        String token = null;

        try {
            java.net.URL url = new URL(address);
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
            JSONObject json = new JSONObject(responseBuilder.toString());
            token = json.getString("token");

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return token;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mListener.onLoginComplete(s);
    }
}
