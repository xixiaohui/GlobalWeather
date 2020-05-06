package com.xixiaohui.weather.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.xixiaohui.weather.view.activity.MainActivity;
import com.xixiaohui.weather.view.activity.SplashActivity;

import java.util.Locale;

import interfaces.heweather.com.interfacesmodule.bean.Lang;


public class LocaleUtil {
    public static final String zh_hans = "zh-hans"; //简体中文
    public static final String zh = "zh"; //简体中文
    public static final String hk = "zh_HK"; //简体中文
    public static final String tw = "zh_TW"; //简体中文

    public static final String zh_hant = "zh-hant"; //繁体中文
    public static final String en = "en"; //en 	英文
    public static final String de = "de";//	德语
    public static final String es = "es";//	西班牙语
    public static final String fr = "fr";//法语
    public static final String it = "it";//意大利语
    public static final String ja = "ja";//日语
    public static final String ko = "ko";//韩语
    public static final String ru = "ru";//俄语
    public static final String hi = "hi";//印地语
    public static final String th = "th";//泰语
    public static final String ar = "ar";//阿拉伯语
    public static final String pt = "pt";//葡萄牙语
    public static final String bn = "bn";//孟加拉语
    public static final String ms = "ms";//马来语
    public static final String nl = "nl";//荷兰语
    public static final String el = "el";//希腊语
    public static final String la = "la";//拉丁语
    public static final String sv = "sv";//瑞典语
    public static final String id = "id";//印尼语
    public static final String pl = "pl";//波兰语
    public static final String tr = "tr";//土耳其语
    public static final String cs = "cs";//捷克语
    public static final String et = "et";//爱沙尼亚语
    public static final String vi = "vi";//越南语
    public static final String fil = "fil";//菲律宾语
    public static final String fi = "fi";//芬兰语
    public static final String he = "he";//希伯来语
    public static final String is = "is";//冰岛语
    public static final String nb = "nb";//挪威语
    public static final String in = "in";//挪威语



    public static Locale getUserLocale() {
        String currentLanguage = SpUtil.getInstance().getString("currentLanguage", null);
        Locale myLocale = Locale.ENGLISH;
        switch (currentLanguage) {
            case zh_hans:
            case zh:
                myLocale = Locale.SIMPLIFIED_CHINESE;
                break;
            case en:
                myLocale = Locale.ENGLISH;
                break;
            case zh_hant:
                myLocale = Locale.TRADITIONAL_CHINESE;
                break;
            case de:
                myLocale = Locale.GERMAN;
                break;
            case ja:
                myLocale = Locale.JAPANESE;
                break;
            case ko:
                myLocale = Locale.KOREAN;
                break;
            case fr:
                myLocale = Locale.FRENCH;
                break;
            case it:
                myLocale = Locale.ITALIAN;
                break;
        }
        return myLocale;
    }

    /**
     * 设置语言：如果之前有设置就遵循设置如果没设置过就跟随系统语言
     */
    public static void changeAppLanguage(Context context) {
        if (context == null) return;
        Context appContext = context.getApplicationContext();
        String currentLanguage = SpUtil.getInstance().getString("currentLanguage", null);
        Locale myLocale;
        switch (currentLanguage) {
            case zh_hans:
            case zh:
                myLocale = Locale.SIMPLIFIED_CHINESE;
                break;
            case en:
                myLocale = Locale.ENGLISH;
                break;
            case zh_hant:
                myLocale = Locale.TRADITIONAL_CHINESE;
                break;
            case de:
                myLocale = Locale.GERMAN;
                break;
            case ja:
                myLocale = Locale.JAPANESE;
                break;
            case ko:
                myLocale = Locale.KOREAN;
                break;
            case fr:
                myLocale = Locale.FRENCH;
                break;
            case it:
                myLocale = Locale.ITALIAN;
                break;
            default:
                myLocale = appContext.getResources().getConfiguration().locale;
                break;
        }
        // 本地语言设置
        if (needUpdateLocale(appContext, myLocale)) {
            updateLocale(appContext, myLocale);
        }
    }

    /**
     * 保存设置的语言     *     * @param currentLanguage index
     */
    public static void changeAppLanguage(Context context, String currentLanguage) {
        if (context == null) return;
        Context appContext = context.getApplicationContext();
        SpUtil.getInstance().save("currentLanguage", currentLanguage);
        Locale myLocale = Locale.SIMPLIFIED_CHINESE;
        switch (currentLanguage) {
            case zh_hans:
            case zh:
                myLocale = Locale.SIMPLIFIED_CHINESE;
                break;
            case en:
                myLocale = Locale.ENGLISH;
                break;
            case zh_hant:
                myLocale = Locale.TRADITIONAL_CHINESE;
                break;
            case de:
                myLocale = Locale.GERMAN;
                break;
            case ja:
                myLocale = Locale.JAPANESE;
                break;
            case ko:
                myLocale = Locale.KOREAN;
                break;
            case fr:
                myLocale = Locale.FRENCH;
                break;
            case it:
                myLocale = Locale.ITALIAN;
                break;
        }
        // 本地语言设置
        if (LocaleUtil.needUpdateLocale(appContext, myLocale)) {
            LocaleUtil.updateLocale(appContext, myLocale);
        }
        Toast.makeText(appContext, "Setting Success.", Toast.LENGTH_SHORT).show();
        restartApp(appContext);
    }

    /**
     * 重启app生效
     *
     * @param context
     */
    public static void restartApp(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 判断需不需要更新     *
     * * @param context Context
     * * @param locale  New User Locale
     * * @return true / false
     */
    public static boolean needUpdateLocale(Context context, Locale locale) {
        return locale != null && !getCurrentLocale(context).equals(locale);
    }

    /**
     * 获取当前的Locale     *
     * * @param context Context
     * * @return Locale
     */
    public static Locale getCurrentLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0有多语言设置获取顶部的语言
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

    /**
     * 更新Locale
     *
     * @param context Context
     * @param locale  New User Locale
     */
    public static void updateLocale(Context context, Locale locale) {
        if (needUpdateLocale(context, locale)) {
            Configuration configuration = context.getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= 19) {
                configuration.setLocale(locale);
            } else {
                configuration.locale = locale;
            }
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            context.getResources().updateConfiguration(configuration, displayMetrics);
        }
    }

    public static void setLanguage(Context context, Configuration newConfig) {
        if (context == null) return;
        Context appContext = context.getApplicationContext();
        String currentLanguage = SpUtil.getInstance().getString("currentLanguage", zh);
        Locale myLocale;

        switch (currentLanguage) {
            case zh_hans:
            case zh:
                myLocale = Locale.SIMPLIFIED_CHINESE;
                break;
            case en:
                myLocale = Locale.ENGLISH;
                break;
            case zh_hant:
                myLocale = Locale.TRADITIONAL_CHINESE;
                break;
            case de:
                myLocale = Locale.GERMAN;
                break;
            case ja:
                myLocale = Locale.JAPANESE;
                break;
            case ko:
                myLocale = Locale.KOREAN;
                break;
            case fr:
                myLocale = Locale.FRENCH;
                break;
            case it:
                myLocale = Locale.ITALIAN;
                break;
            default:
                myLocale = appContext.getResources().getConfiguration().locale;
        }
        // 系统语言改变了应用保持之前设置的语言
        if (myLocale != null) {
            Locale.setDefault(myLocale);
            Configuration configuration = new Configuration(newConfig);
            if (Build.VERSION.SDK_INT >= 19) {
                configuration.setLocale(myLocale);
            } else {
                configuration.locale = myLocale;
            }
            appContext.getResources().updateConfiguration(configuration, appContext.getResources().getDisplayMetrics());
        }
    }

    public static Lang getLangByLocale(){
        String value= Locale.getDefault().getLanguage();

        Lang lang;
        switch (value){
            case en:
                lang = Lang.ENGLISH;
                break;
            case zh:
                lang = Lang.CHINESE_SIMPLIFIED;
                break;
            case hk:
            case tw:
                lang = Lang.CHINESE_TRADITIONAL;
                break;
            case de:
                lang = Lang.GERMAN;
                break;
            case es:
                lang = Lang.SPANISH;
                break;
            case fr:
                lang = Lang.FRENCH;
                break;
            case it:
                lang = Lang.ITALIAN;
                break;
            case ja:
                lang = Lang.JAPANESE;
                break;
            case ko:
                lang = Lang.KOREAN;
                break;
            case ru:
                lang = Lang.RUSSIAN;
                break;
            case in:
                lang = Lang.HINDI;
                break;
            case th:
                lang = Lang.THAI;
                break;
            default:
                lang = Lang.ENGLISH;
        }
        return lang;
    }
}