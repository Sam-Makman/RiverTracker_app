package com.makman.rivertracker;

import android.graphics.drawable.Drawable;

/**
 * Created by sam on 5/1/16.
 */
public final class ConditionMapper {

    public static int map(String con){

        if(con.contains("Partly")){
            return  R.drawable.partially_sunny;
        }if(con.contains("Showers")){
            return R.drawable.rainy;
        } if(con.contains("Sunny") || con.contains("Clear")){
            return  R.drawable.sunny;
        }if(con.contains("Partly")){
            return  R.drawable.partially_sunny;
        } if(con.contains("Hail")){
            return R.drawable.hail;
        } if(con.contains("Snow")){
            return R.drawable.snowy;
        } if(con.contains("Thunder")){
            return R.drawable.thunder;
        }
        return -1;
    }

}
