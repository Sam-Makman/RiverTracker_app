package com.makman.rivertracker;

/**
 * Created by prog on 4/14/16.
 */
public class Alert {
    private String mTitle;
    private String mDescription;
    private String mDate;

    public Alert(String title, String description, String date){
        mTitle=title;
        mDescription=description;
        mDate=date;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmDate() {
        return mDate;
    }
}
