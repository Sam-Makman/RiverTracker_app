package com.makman.rivertracker.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.makman.rivertracker.Alert;
import com.makman.rivertracker.ConditionMapper;
import com.makman.rivertracker.Models.Weather;
import com.makman.rivertracker.NetworkTasks.VolleyNetworkTask;
import com.makman.rivertracker.R;
import com.makman.rivertracker.River;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;


public class WeatherFragment extends Fragment implements Response.Listener<JSONObject> , Response.ErrorListener{

    public static final String ARG_RIVER = "ARG_RIVER";
    public static final String WEATHER_URL = "http://forecast.weather.gov/MapClick.php?FcstType=json&lat=";
    private static final String TAG = WeatherFragment.class.getSimpleName();
    private River mRiver;
    private Weather mWeather;


    @Bind(R.id.fragment_weather_temp)
    TextView mTempurature;

    @Bind(R.id.fragment_weather_conditions)
    TextView mConditions;

    @Bind(R.id.fragment_weather_wind)
    TextView mWind;

    @Bind(R.id.fragment_weather_image)
    ImageView mImage;


    public static WeatherFragment newInstance(River river) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RIVER, river);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        mRiver = getArguments().getParcelable(ARG_RIVER);
        if(mRiver == null){return view;}
        String[] loc = mRiver.getPut_in().split(",");
        Double lat = Double.parseDouble(loc[0]);
        Double lon = Double.parseDouble(loc[1]);
        String url = WEATHER_URL + lat + "&lon=" + lon;
        Log.d(TAG, url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleyNetworkTask.getInstance().getRequestQueue().add(request);
        return view;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "Cannot Load Weather", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONObject curWeather = null;
        String description = null;
        try {
            curWeather = response.getJSONObject("currentobservation");
            description = (String) response.getJSONObject("data").getJSONArray("weather").get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int wImg = ConditionMapper.map(description);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && wImg != -1) {
            mImage.setImageDrawable(getContext().getDrawable(wImg));
        }else{
            mImage.setVisibility(View.GONE);
        }

        mWeather = new Gson().fromJson(curWeather.toString(), Weather.class);

        mWind.setText(String.format(getContext().getString(R.string.weather_wind),mWeather.getWinds()));
        mConditions.setText(String.format(getContext().getString(R.string.weather_conditions),description));
        mTempurature.setText(String.format(getContext().getString(R.string.weather_temp),mWeather.getTemp()));
    }
}
