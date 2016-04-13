package com.makman.rivertracker.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.makman.rivertracker.FavoritesActivity;
import com.makman.rivertracker.NetworkTasks.VolleyNetworkTask;
import com.makman.rivertracker.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

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

        if(!userInfo[1].equals(userInfo[2])){
            Toast.makeText(this, "Mismatched passwords", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response).getJSONObject("form");
                                String site = jsonResponse.getString("site"),
                                        network = jsonResponse.getString("network");
                                System.out.println("Site: "+site+"\nNetwork: "+network);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<>();
                    // the POST parameters:
                    params.put("", userInfo[0]);
                    params.put("", userInfo[1]);
                    params.put("", userInfo[2]);
                    return params;
                }
            };
            VolleyNetworkTask.getInstance().getRequestQueue().add(postRequest);
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


}
