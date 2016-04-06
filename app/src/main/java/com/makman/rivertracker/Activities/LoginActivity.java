package com.makman.rivertracker.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.makman.rivertracker.FavoritesActivity;
import com.makman.rivertracker.NetworkTasks.LoginNetworkTask;
import com.makman.rivertracker.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginNetworkTask.LoginListener {

    public static final String TOKEN = "token";
    public static final String PREFERENCES = "TOKEN_PREFERENCES";

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.login_button_login)
    Button mButton;

    @Bind(R.id.login_edit_text_email)
    EditText mEmail;

    @Bind(R.id.login_edit_text_password)
    EditText mPassword;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.login_button_login) void onClick(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        if(email.isEmpty()){
            Toast.makeText(this, R.string.login_enter_email, Toast.LENGTH_SHORT).show();
        }else if( password.isEmpty()){
            Toast.makeText(this, R.string.login_enter_pass, Toast.LENGTH_SHORT).show();
        }else{
            mTask = new LoginNetworkTask(email, password, this);
            mTask.execute();
            mButton.setEnabled(false);
        }
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
