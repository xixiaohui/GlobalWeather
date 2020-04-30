package com.xixiaohui.weather.globalweather.until

import com.xixiaohui.weather.MyApplication
import com.xixiaohui.weather.utils.SpUtils


class ContentUtil {

    companion object{


        //用户id
        val APK_USERNAME = "HE2004222028131304"
        //用户key
        val APK_KEY = "5737e4be7f3f47f9bc6f988573365c37"


        //当前所在位置
        //经度
        var NOW_LON = 40.77899933;
        //纬度
        var NOW_LAT = -73.96900177;

        //当前城市
        var NOW_CITY_ID = SpUtils.getString(MyApplication.getContext(), "lastLocation", "US3290117");
        var  NOW_CITY_NAME = SpUtils.getString(MyApplication.getContext(), "nowCityName", "New York");

        val FIRST_OPEN = SpUtils.getString(MyApplication.getContext(), "first_open", "true");

        //应用设置里的文字
        val SYS_LANG = "zh";

        var APP_SETTING_LANG = SpUtils.getString(MyApplication.getContext(), "language", "sys");

        val APP_SETTING_UNIT = SpUtils.getString(MyApplication.getContext(), "unit", "she");
        val  APP_SETTING_TESI = SpUtils.getString(MyApplication.getContext(), "size", "mid");
        val  APP_PRI_TESI = SpUtils.getString(MyApplication.getContext(), "size", "mid");
        val  APP_SETTING_THEME = SpUtils.getString(MyApplication.getContext(), "theme", "浅色");


        val  UNIT_CHANGE = false;
        val  CHANGE_LANG = false;
        val  CITY_CHANGE = false;
    }



}