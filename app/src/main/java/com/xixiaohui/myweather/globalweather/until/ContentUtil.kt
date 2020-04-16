package com.xixiaohui.myweather.globalweather.until

import com.xixiaohui.myweather.globalweather.com.xixiaohui.myweather.globalweather.MyApplication


class ContentUtil {

    //用户id
    val APK_USERNAME = "HE1903211415311839";
    //用户key
    val  APK_KEY = "a7b3fdf76a2b44fc91e36b2afe63d080";

    //当前所在位置
    val  NOW_LON = 74.00;
    val NOW_LAT = 40.43;

    //当前城市
    val NOW_CITY_ID = SpUtils.getString(MyApplication.getContext(), "lastLocation", "US3290117");
    val  NOW_CITY_NAME = SpUtils.getString(MyApplication.getContext(), "nowCityName", "纽约");

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