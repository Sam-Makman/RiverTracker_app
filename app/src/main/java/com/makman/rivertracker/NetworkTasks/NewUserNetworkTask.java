package com.makman.rivertracker.NetworkTasks;

import android.os.AsyncTask;
import com.makman.rivertracker.Models.User;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sam on 4/6/16.
 */
public class NewUserNetworkTask extends AsyncTask<User, Integer, String> {
    public static final String NEW_USER_URL = "https://radiant-temple-90497.herokuapp.com/api/signup";

    @Override
    protected String doInBackground(User... params) {
        try {
            URL url = new URL(NEW_USER_URL);
            User user = params[0];
            if(user == null){
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return null;
    }
}
