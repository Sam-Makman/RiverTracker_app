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


public class SignUpActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private static final String URL = "https://radiant-temple-90497.herokuapp.com/api/signup";
    private static final String BACKGROUND_URL = "http://res.cloudinary.com/hgsa3o7eg/image/upload/v1461118214/12998153_996950453716331_4774880468651775980_o_rhp348.jpg";

    private final String[] userInfo = new String[4];


    @Bind(R.id.signup_edit_text_email)
    EditText mEmail;

    @Bind(R.id.signup_edit_text_password)
    EditText mPassword;

    @Bind(R.id.signup_edit_text_password_confirm)
    EditText mConfirm;

    @Bind(R.id.signup_button_signup)
    Button mSignup;

    @Bind(R.id.signup_button_have_account)
    Button mLogin;

    @Bind(R.id.signup_button_no_account)
    Button mContinue;

    @Bind(R.id.signup_name_edit_text)
    EditText mName;

    @Bind(R.id.signup_background)
    ImageView mBackground;

    @Bind(R.id.signup_progress_bar)
    ProgressBar mProgress;

    SharedPreferences mPreference;
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        SharedPreferences pref = getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE);

        ActionBar bar = getSupportActionBar();
        bar.hide();

        Picasso.with(this).load(BACKGROUND_URL).fit().centerCrop().into(mBackground);

        if(!pref.getString(LoginActivity.TOKEN, "").equals("")){
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.signup_button_signup) void signup(){
        userInfo[0] = mName.getText().toString();
        userInfo[1] = mEmail.getText().toString();
        userInfo[2] = mPassword.getText().toString();
        userInfo[3] = mConfirm.getText().toString();

        mSignup.setEnabled(false);
        if(userInfo[0].equals("") || userInfo[1].equals("") || userInfo[2].equals("") || userInfo[2].equals("")){
            Toast.makeText(this, R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
            mSignup.setEnabled(true);
        }
        else if(!userInfo[2].equals(userInfo[3])){
            Toast.makeText(this, R.string.mismatched_passwords, Toast.LENGTH_SHORT).show();
            mSignup.setEnabled(true);
        }else{

            JSONObject user = new JSONObject();
            JSONObject params = new JSONObject();
            try {
                mProgress.setVisibility(View.VISIBLE);
                params.put("name", userInfo[0]);
                params.put("email", userInfo[1]);
                params.put("password", userInfo[2]);
                params.put("password_confirm", userInfo[3]);
                user.put("user", params);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, user, this, this);
            VolleyNetworkTask.getInstance().getRequestQueue().add(jsonObjectRequest);
        }
    }

    @OnClick(R.id.signup_button_have_account) void login(){
        VolleyNetworkTask.getInstance().getRequestQueue().cancelAll(this);
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.signup_button_no_account) void tryItOut(){
        VolleyNetworkTask.getInstance().getRequestQueue().cancelAll(this);
        Intent intent = new Intent(SignUpActivity.this, FavoritesActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();

        mProgress.setVisibility(View.GONE);
        Toast.makeText(this, R.string.signup_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        mPreference = getSharedPreferences(LoginActivity.PREFERENCES, Context.MODE_PRIVATE);
        mEditor = mPreference.edit();
        mProgress.setVisibility(View.GONE);

        mSignup.setEnabled(true);

        try {
            if(response.has("token")){
                String token = response.getString("token");
                mEditor.putString(LoginActivity.TOKEN, token);
                Log.d(TAG, token);
                Log.d(TAG, response.toString());
                mEditor.apply();
                Intent intent = new Intent(SignUpActivity.this, FavoritesActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, R.string.signup_failed, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.signup_failed, Toast.LENGTH_SHORT).show();

        }

    }
}
