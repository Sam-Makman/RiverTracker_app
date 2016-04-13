package com.makman.rivertracker.NetworkTasks;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by prog on 4/13/16.
 */
public class VolleyNetworkTask extends Application {
    private static VolleyNetworkTask sInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
    }

    public synchronized static VolleyNetworkTask getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
