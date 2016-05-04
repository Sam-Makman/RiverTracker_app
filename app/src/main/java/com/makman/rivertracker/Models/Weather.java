package com.makman.rivertracker.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sam on 5/1/16.
 */
public class Weather implements Parcelable {

    /**
     * id : KNYC
     * name : New York City, Central Park
     * elev : 154
     * latitude : 40.78
     * longitude : -73.97
     * Date : 1 May 17:51 pm EDT
     * Temp : 48
     * Dewp : 45
     * Relh : 89
     * Winds : 6
     * Windd : 999
     * Gust : 0
     * Weather :  Light Rain Fog/Mist
     * Weatherimage : ra.png
     * Visibility : 2.00
     * Altimeter : 1019.6
     * SLP : 30.13
     * timezone : EDT
     * state : NY
     * WindChill : 45
     */

    private String id;
    private String name;
    private String elev;
    private String latitude;
    private String longitude;
    private String Date;
    private String Temp;
    private String Dewp;
    private String Relh;
    private String Winds;
    private String Windd;
    private String Gust;
    private String Weather;
    private String Weatherimage;
    private String Visibility;
    private String Altimeter;
    private String SLP;
    private String timezone;
    private String state;
    private String WindChill;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElev() {
        return elev;
    }

    public void setElev(String elev) {
        this.elev = elev;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String Temp) {
        this.Temp = Temp;
    }

    public String getDewp() {
        return Dewp;
    }

    public void setDewp(String Dewp) {
        this.Dewp = Dewp;
    }

    public String getRelh() {
        return Relh;
    }

    public void setRelh(String Relh) {
        this.Relh = Relh;
    }

    public String getWinds() {
        return Winds;
    }

    public void setWinds(String Winds) {
        this.Winds = Winds;
    }

    public String getWindd() {
        return Windd;
    }

    public void setWindd(String Windd) {
        this.Windd = Windd;
    }

    public String getGust() {
        return Gust;
    }

    public void setGust(String Gust) {
        this.Gust = Gust;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String Weather) {
        this.Weather = Weather;
    }

    public String getWeatherimage() {
        return Weatherimage;
    }

    public void setWeatherimage(String Weatherimage) {
        this.Weatherimage = Weatherimage;
    }

    public String getVisibility() {
        return Visibility;
    }

    public void setVisibility(String Visibility) {
        this.Visibility = Visibility;
    }

    public String getAltimeter() {
        return Altimeter;
    }

    public void setAltimeter(String Altimeter) {
        this.Altimeter = Altimeter;
    }

    public String getSLP() {
        return SLP;
    }

    public void setSLP(String SLP) {
        this.SLP = SLP;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWindChill() {
        return WindChill;
    }

    public void setWindChill(String WindChill) {
        this.WindChill = WindChill;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.elev);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.Date);
        dest.writeString(this.Temp);
        dest.writeString(this.Dewp);
        dest.writeString(this.Relh);
        dest.writeString(this.Winds);
        dest.writeString(this.Windd);
        dest.writeString(this.Gust);
        dest.writeString(this.Weather);
        dest.writeString(this.Weatherimage);
        dest.writeString(this.Visibility);
        dest.writeString(this.Altimeter);
        dest.writeString(this.SLP);
        dest.writeString(this.timezone);
        dest.writeString(this.state);
        dest.writeString(this.WindChill);
    }

    public Weather() {
    }

    protected Weather(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.elev = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.Date = in.readString();
        this.Temp = in.readString();
        this.Dewp = in.readString();
        this.Relh = in.readString();
        this.Winds = in.readString();
        this.Windd = in.readString();
        this.Gust = in.readString();
        this.Weather = in.readString();
        this.Weatherimage = in.readString();
        this.Visibility = in.readString();
        this.Altimeter = in.readString();
        this.SLP = in.readString();
        this.timezone = in.readString();
        this.state = in.readString();
        this.WindChill = in.readString();
    }

    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
}
