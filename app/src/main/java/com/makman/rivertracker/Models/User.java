package com.makman.rivertracker.Models;

import org.json.JSONObject;

/**
 * Created by sam on 4/6/16.
 */
public class User {
    private String mName;
    private String mPassword;
    private String mPasswordConfirm;

    JSONObject toJson(){

        return null;
    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmPasswordConfirm() {
        return mPasswordConfirm;
    }

    public void setmPasswordConfirm(String mPasswordConfirm) {
        this.mPasswordConfirm = mPasswordConfirm;
    }
}
