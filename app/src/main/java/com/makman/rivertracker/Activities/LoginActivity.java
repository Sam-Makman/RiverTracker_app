package com.makman.rivertracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.makman.rivertracker.FavoritesActivity;
import com.makman.rivertracker.NetworkTasks.VolleyNetworkTask;
import com.makman.rivertracker.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements  Response.ErrorListener, Response.Listener<JSONObject> {
    public static final String URL = "https://radiant-temple-90497.herokuapp.com/api/login?email=";
    public static final String TOKEN = "token";
    public static final String PREFERENCES = "TOKEN_PREFERENCES";
    public static final String LOGIN_IMAGE_URL = "http://res.cloudinary.com/hgsa3o7eg/image/upload/v1460417369/12711085_961532673896473_5590962366776566471_o_hansxg.jpg";
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.login_button_login)
    Button mButton;

    @Bind(R.id.login_button_signup)
    Button mSignup;

    @Bind(R.id.login_edit_text_email)
    EditText mEmail;

    @Bind(R.id.login_edit_text_password)
    EditText mPassword;

    @Bind(R.id.login_image_view)
    ImageView image;

    @Bind(R.id.login_progress_bar)
    ProgressBar mProgressBar;

    SharedPreferences mPreference;
    SharedPreferences.Editor mEditor;


    /**
     * If login token exists in shared preferences skips to favorites activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE);

        Log.d(TAG,pref.getString(LoginActivity.TOKEN, ""));
        if(!pref.getString(LoginActivity.TOKEN, "").equals("")){
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
            finish();
        }

        ButterKnife.bind(this);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        Picasso.with(this).load(LOGIN_IMAGE_URL).fit().centerCrop().into(image);

    }

    /**
     * validates user info then sends volley request
     * to api
     */
    @OnClick(R.id.login_button_login)
    void onClick(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String url = URL+ email + "&password=" + password;
        mProgressBar.setVisibility(View.VISIBLE);
        mButton.setEnabled(false);
        if(email.isEmpty()){
            Toast.makeText(this, R.string.login_enter_email, Toast.LENGTH_SHORT).show();
            mButton.setEnabled(true);
        }else if( password.isEmpty()){
            Toast.makeText(this, R.string.login_enter_pass, Toast.LENGTH_SHORT).show();
            mButton.setEnabled(true);
        }else{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            VolleyNetworkTask.getInstance().getRequestQueue().add(jsonObjectRequest);
        }
    }

    /**
     * goes to signup activity
     * cancels all volley tasks
     */
    @OnClick(R.id.login_button_signup)
    void signup(){
        VolleyNetworkTask.getInstance().getRequestQueue().cancelAll(this);
        mSignup.setEnabled(false);
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * exits app
     */
//    @Override
//    public void onBackPressed() {
//        finish();
//    }

    /**
     * allows user to try to login again if network error
     * @param error volley network error
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,R.string.login_failed, Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
        mButton.setEnabled(true);
    }

    /**
     * gets api token if sucessful login
     * goes to favorites activity if sucess
     * allows more login attemps if failure
     * @param response json response from api
     */
    @Override
    public void onResponse(JSONObject response) {
        mPreference = getSharedPreferences(LoginActivity.PREFERENCES,Context.MODE_PRIVATE);
        mEditor = mPreference.edit();

        mProgressBar.setVisibility(View.GONE);
        mButton.setEnabled(true);
        try {
            if(response.has("token")){
                mEditor.putString(TOKEN, response.getString("token"));
                Log.d(TAG, response.getString("token"));
                Log.d(TAG, response.toString());
                mEditor.apply();
                Intent intent = new Intent(LoginActivity.this, FavoritesActivity.class);
                startActivity(intent);
                finish();
        }else{
                Toast.makeText(this, R.string.login_failed, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,R.string.login_failed, Toast.LENGTH_SHORT).show();
        }
    }
}
