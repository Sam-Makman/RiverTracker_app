package com.makman.rivertracker.NetworkTasks;

import android.app.Application;
import android.text.format.Time;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Created by Sam.
 */
public class VolleyNetworkTask extends Application {
    public static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 200 * 1024 * 1024;
    private static final String TAG = VolleyNetworkTask.class.getSimpleName();
    private static VolleyNetworkTask sInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        final Picasso built = builder.build();
//        built.setIndicatorsEnabled(true);
        Picasso.setSingletonInstance(built);
    }

    public synchronized static VolleyNetworkTask getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
