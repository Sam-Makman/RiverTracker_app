package com.makman.rivertracker.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.makman.rivertracker.FavoritesActivity;
import com.makman.rivertracker.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

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
