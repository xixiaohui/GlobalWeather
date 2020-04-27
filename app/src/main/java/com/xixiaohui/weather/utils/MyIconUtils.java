package com.xixiaohui.weather.utils;

import com.xixiaohui.weather.R;

public class MyIconUtils {

    public static int getWeatherIcon(String weather) {
        int imageId;
        switch (weather){
            case "100":
                imageId = R.mipmap.sunny;
                break;
            case "101":
            case "104":
                imageId = R.mipmap.cloudy;
                break;
            case "314":
            case "315":
            case "316":
                imageId = R.mipmap.rainy;
                break;
            default:
                imageId = R.mipmap.sunny;
                break;
        }
        return imageId;
    }
}
