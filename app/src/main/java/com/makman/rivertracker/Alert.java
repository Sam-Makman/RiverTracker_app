package com.makman.rivertracker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by prog on 4/14/16.
 */
public class Alert implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeString(this.mDate);
    }

    protected Alert(Parcel in) {
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mDate = in.readString();
    }

    public static final Parcelable.Creator<Alert> CREATOR = new Parcelable.Creator<Alert>() {
        @Override
        public Alert createFromParcel(Parcel source) {
            return new Alert(source);
        }

        @Override
        public Alert[] newArray(int size) {
            return new Alert[size];
        }
    };
}
