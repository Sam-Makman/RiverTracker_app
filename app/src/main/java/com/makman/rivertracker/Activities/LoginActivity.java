package com.makman.rivertracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.makman.rivertracker.FavoritesActivity;
import com.makman.rivertracker.NetworkTasks.LoginNetworkTask;
import com.makman.rivertracker.NetworkTasks.VolleyNetworkTask;
import com.makman.rivertracker.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginNetworkTask.LoginListener {
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

    LoginNetworkTask mTask;
    SharedPreferences mPreference;
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPreference = getSharedPreferences(LoginActivity.PREFERENCES,Context.MODE_PRIVATE);
        mEditor = mPreference.edit();
        ActionBar bar = getSupportActionBar();
        bar.hide();
        Picasso.with(this).load(LOGIN_IMAGE_URL).fit().centerCrop().into(image);

    }

    @OnClick(R.id.login_button_login) void onClick(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String url = URL+ email + "&password=" + password;

        if(email.isEmpty()){
            Toast.makeText(this, R.string.login_enter_email, Toast.LENGTH_SHORT).show();
        }else if( password.isEmpty()){
            Toast.makeText(this, R.string.login_enter_pass, Toast.LENGTH_SHORT).show();
        }else{
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        mPreference = getSharedPreferences(LoginActivity.PREFERENCES,Context.MODE_PRIVATE);
                        mEditor = mPreference.edit();
                        //response = response.getJSONObject("args");
                        mEditor.putString(TOKEN, response.getString("token"));
                        Log.d(TAG, response.getString("token"));
                        Log.d(TAG, response.toString());
                        mEditor.apply();
                        Intent intent = new Intent(LoginActivity.this, FavoritesActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            VolleyNetworkTask.getInstance().getRequestQueue().add(jsonObjectRequest);

//            mTask = new LoginNetworkTask(email, password, this);
//            mTask.execute();
//            mButton.setEnabled(false);
        }
    }
    @OnClick(R.id.login_button_signup)
    void signup(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginComplete(String token) {
        if(token == null || token.isEmpty() ){
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            mButton.setEnabled(true);
        }else {
            Log.d(TAG, "Storing token");
            mEditor.putString(TOKEN, token);
            mEditor.apply();
            Intent intent = new Intent(LoginActivity.this, FavoritesActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}
