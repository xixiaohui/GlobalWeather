package com.heweather.owp.globalweather.until

import com.heweather.owp.MyApplication
import com.heweather.owp.until.SpUtils


class ContentUtil {

    companion object{


        //用户id
        //    public static final String APK_USERNAME = "HE2004091051171061";
        //用户key
        //    public static final String APK_KEY = "4d7b5da90d924c27a351caf1c76a388e";
        val APK_USERNAME = "HE1903211415311839"

        //用户key
        val APK_KEY = "a7b3fdf76a2b44fc91e36b2afe63d080"


        //当前所在位置
        //经度
        var NOW_LON = 117.134388;
        //纬度
        var NOW_LAT = 31.839022;

        //当前城市
        var NOW_CITY_ID = SpUtils.getString(MyApplication.getContext(), "lastLocation", "US3290117");
        var  NOW_CITY_NAME = SpUtils.getString(MyApplication.getContext(), "nowCityName", "纽约");

        val FIRST_OPEN = SpUtils.getString(MyApplication.getContext(), "first_open", "true");

        //应用设置里的文字
        val SYS_LANG = "zh";
        val APP_SETTING_LANG = SpUtils.getString(MyApplication.getContext(), "language", "sys");
        val APP_SETTING_UNIT = SpUtils.getString(MyApplication.getContext(), "unit", "she");
        val  APP_SETTING_TESI = SpUtils.getString(MyApplication.getContext(), "size", "mid");
        val  APP_PRI_TESI = SpUtils.getString(MyApplication.getContext(), "size", "mid");
        val  APP_SETTING_THEME = SpUtils.getString(MyApplication.getContext(), "theme", "浅色");


        val  UNIT_CHANGE = false;
        val  CHANGE_LANG = false;
        val  CITY_CHANGE = false;
    }



}