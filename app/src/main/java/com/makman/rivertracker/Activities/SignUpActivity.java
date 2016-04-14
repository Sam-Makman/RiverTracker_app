package com.makman.rivertracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.makman.rivertracker.FavoritesActivity;
import com.makman.rivertracker.NetworkTasks.VolleyNetworkTask;
import com.makman.rivertracker.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private static final String URL = "https://radiant-temple-90497.herokuapp.com/api/signup";
    private final String[] userInfo = new String[3];


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

    SharedPreferences mPreference;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        SharedPreferences pref = getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE);

        if(!pref.getString(LoginActivity.TOKEN, "").equals("")){
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.signup_button_signup) void signup(){
        userInfo[0] = mEmail.getText().toString();
        userInfo[1] = mPassword.getText().toString();
        userInfo[2] = mConfirm.getText().toString();

        if(!userInfo[1].equals(userInfo[2])) {
            Toast.makeText(this, "Mismatched passwords", Toast.LENGTH_SHORT).show();
        }else{
            JSONObject user = new JSONObject();
            JSONObject params = new JSONObject();
            try {
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
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.signup_button_no_account) void tryItOut(){
        Intent intent = new Intent(SignUpActivity.this, FavoritesActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        mPreference = getSharedPreferences(LoginActivity.PREFERENCES, Context.MODE_PRIVATE);
        mEditor = mPreference.edit();
        //response = response.getJSONObject("args");
        try {
            mEditor.putString(LoginActivity.TOKEN, response.getString("token"));
            Log.d(TAG, response.getString("token"));
            Log.d(TAG, response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mEditor.apply();
        Intent intent = new Intent(SignUpActivity.this, FavoritesActivity.class);
        startActivity(intent);
        finish();
    }
}
