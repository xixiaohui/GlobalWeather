package com.xixiaohui.weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xixiaohui.weather.data.Base;
import com.xixiaohui.weather.data.Forecast;
import com.xixiaohui.weather.data.Now;
import com.xixiaohui.weather.view.activity.Key;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.ForecastBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;

/**
 * Created by niuchong on 2018/5/29.
 */

public class SpUtils {

    public static String PREFERENCE_NAME = "GlobalWeather";


    /**
     * 清空统计数据
     *
     * @param context
     */
    public static void clearSp(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear().commit();
    }

    /**
     * put string preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
     * name that is not a string
     * @see #getString(Context, String, String)
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a string
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * put int preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a int
     * @see #getInt(Context, String, int)
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a int
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a long
     * @see #getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a float
     * @see #getFloat(Context, String, float)
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a float
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
     * name that is not a boolean
     * @see #getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * get boolean preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }


    public static String SceneList2String(HashMap<String, Integer> hashmap)
            throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(hashmap);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, Integer> String2SceneList(
            String SceneListString) throws StreamCorruptedException,
            IOException, ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        HashMap<String, Integer> SceneList = (HashMap<String, Integer>) objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
    }

    public static boolean putHashMap(Context context, String key,
                                     HashMap<String, Integer> hashmap) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        try {
            String listStr = SceneList2String(hashmap);
            editor.putString(key, listStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return editor.commit();
    }

    public static HashMap<String, Integer> getHashMap(Context context,
                                                      String key) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        String listStr = settings.getString(key, "");
        try {
            return String2SceneList(listStr);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存List<String>
     *
     * @param key
     * @param values
     */
    public static boolean putStringList(Context context, String key, List<String> values) {
        SharedPreferences.Editor edit = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        edit.putInt(key, values.size());
        for (int i = 0; i < values.size(); i++) {
            edit.putString(key + i, values.get(i));
        }
        return edit.commit();
    }

    /**
     * 获取List<String>
     *
     * @param key
     * @return
     */
    public static List<String> getStringList(Context context, String key) {
        List<String> values = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        int listSize = sp.getInt(key, 0);
        for (int i = 0; i < listSize; i++) {
            values.add(sp.getString(key + i, null));
        }
        return values;
    }

    /**
     * 保存List<Integer>
     *
     * @param key
     * @param values
     */
    public static boolean putIntList(Context context, String key, List<Integer> values) {
        SharedPreferences.Editor edit = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        edit.putInt(key, values.size());
        for (int i = 0; i < values.size(); i++) {
            edit.putInt(key + i, values.get(i));
        }
        return edit.commit();
    }

    /**
     * 获取List<Integer>
     *
     * @param key
     * @return
     */
    public static List<Integer> getIntList(Context context, String key) {
        List<Integer> values = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        int listSize = sp.getInt(key, 0);
        for (int i = 0; i < listSize; i++) {
            values.add(sp.getInt(key + i, 0));
        }
        return values;
    }

    /**
     * 保存List<Long>
     *
     * @param key
     * @param values
     */
    public static boolean putLongList(Context context, String key, List<Long> values) {
        SharedPreferences.Editor edit = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        edit.putInt(key, values.size());
        for (int i = 0; i < values.size(); i++) {
            edit.putLong(key + i, values.get(i));
        }
        return edit.commit();
    }

    /**
     * 获取List<Long>
     *
     * @param key
     * @return
     */
    public static List<Long> getLongList(Context context, String key) {
        List<Long> values = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        int listSize = sp.getInt(key, 0);
        for (int i = 0; i < listSize; i++) {
            values.add(sp.getLong(key + i, 0));
        }
        return values;
    }

    /**
     * 保存List<Object>
     *
     * @param context
     * @param key
     * @param datalist
     */
    public <T> void putListBean(Context context, String key, List<T> datalist) {
        SharedPreferences.Editor edit = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        if (null == datalist || datalist.size() <= 0) {
            return;
        }
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        edit.putString(key, strJson);
        edit.commit();
    }

    /**
     * 获取List<Object>
     *
     * @param context
     * @param key
     * @return listBean
     */
    public <T> List<T> getListBean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        List<T> dataList = new ArrayList<T>();
        String strJson = sp.getString(key, null);
        if (null == strJson) {
            return dataList;
        }
        Gson gson = new Gson();
        dataList = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return dataList;
    }

    /**
     * 存放实体类以及任意类型
     *
     * @param context 上下文对象
     * @param key
     * @param obj
     */
    public static void saveBean(Context context, String key,
                                Object obj) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String objString = gson.toJson(obj);
        editor.putString(key, objString).commit();
    }

    /**
     * @param context
     * @param key
     * @param clazz   这里传入一个类就是我们所需要的实体类(obj)
     * @return 返回我们封装好的该实体类(obj)
     */
    public static <T> T getBean(Context context, String key,
                                Class<T> clazz) {
        String objString = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(key, "");
        Gson gson = new Gson();
        return gson.fromJson(objString, clazz);
    }

    public static <T> T getBean(Context context, String key,
                                Type typeOfT) {
        String objString = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(key, "");
        Gson gson = new Gson();
        return gson.fromJson(objString, typeOfT);
    }

    public static Boolean isHaveData(Context context, String key) {
        String objString = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(key, "");
        return objString != null && objString != "";
    }

    public static NowBase toNowBaseFromNow(Now now) {
        NowBase nowBase = new NowBase();
        nowBase.setCloud(now.cloud);
        nowBase.setCond_code(now.getCond_code());
        nowBase.setCond_txt(now.cond_txt);
        nowBase.setFl(now.fl);
        nowBase.setHum(now.hum);
        nowBase.setPres(now.pres);
        nowBase.setVis(now.vis);
        nowBase.setTmp(now.tmp);
        nowBase.setWind_deg(now.wind_deg);
        nowBase.setWind_dir(now.wind_dir);
        nowBase.setWind_spd(now.wind_spd);
        nowBase.setWind_sc(now.wind_sc);
        return nowBase;
    }

    public static Basic toBasicFromBase(Base base) {
        Basic basic = new Basic();
        basic.setAdmin_area(base.admin_area);
        basic.setCid(base.getCid());
        basic.setCnty(base.cnty);
        basic.setLat(base.lat);
        basic.setLocation(base.location);
        basic.setLon(base.lon);
        basic.setTz(base.tz);
        basic.setParent_city(base.parent_city);
        return basic;
    }

    public static ForecastBase toForecastBaseFromForecast(Forecast forecast) {
        ForecastBase forecastBase = new ForecastBase();

        forecastBase.setCond_code_d(forecast.getCond_code_d());
        forecastBase.setCond_code_n(forecast.cond_code_n);
        forecastBase.setCond_txt_d(forecast.cond_txt_d);
        forecastBase.setCond_txt_n(forecast.cond_txt_n);
        forecastBase.setDate(forecast.date);
        forecastBase.setHum(forecast.hum);
        forecastBase.setMr(forecast.mr);
        forecastBase.setMs(forecast.ms);
        forecastBase.setPcpn(forecast.pcpn);
        forecastBase.setPop(forecast.pop);
        forecastBase.setPres(forecast.pres);
        forecastBase.setSr(forecast.sr);
        forecastBase.setSs(forecast.ss);
        forecastBase.setTmp_max(forecast.tmp_max);
        forecastBase.setTmp_min(forecast.tmp_min);
        forecastBase.setUv_index(forecast.uv_index);
        forecastBase.setVis(forecast.vis);
        forecastBase.setWind_deg(forecast.wind_deg);
        forecastBase.setWind_dir(forecast.wind_dir);
        forecastBase.setWind_sc(forecast.wind_sc);
        forecastBase.setWind_spd(forecast.wind_spd);

        return forecastBase;
    }
    public static final int EN = 0;
    public static final int ZH = 1;

    public static String getWeek(Date date, String value) {
        String[] weeks = {};
        switch (value) {
            case LocaleUtil.zh_hans:
            case LocaleUtil.zh:
            case LocaleUtil.hk:
            case LocaleUtil.tw:
                weeks = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
                break;
            case LocaleUtil.de:
                weeks = new String[]{"Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"};
                break;
            case LocaleUtil.es:
                weeks = new String[]{"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
                break;
            case LocaleUtil.fr:
                weeks = new String[]{"Dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"};
                break;
            case LocaleUtil.it:
                weeks = new String[]{"Domenica", "lunedì", "martedì", "mercoledì", "giovedì", "venerdì", "sabato"};
                break;
            case LocaleUtil.ja:
                weeks = new String[]{"日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"};
                break;
            case LocaleUtil.ko:
                weeks = new String[]{"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
                break;
            case LocaleUtil.ru:
                weeks = new String[]{"Воскресенье", "понедельник", "вторник", "среда", "четверг", "пятница", "суббота"};
                break;
            case LocaleUtil.in:
                weeks = new String[]{"रविवार", "सोमवार", "मंगलवार", "बुधवार", "गुरुवार", "शुक्रवार", "शनिवार"};
                break;
            case LocaleUtil.th:
                weeks = new String[]{"วันอาทิตย์", "วันจันทร์", "วันอังคาร", "วันพุธ", "วันพฤหัสบดี", "วันศุกร์", "วันเสาร์"};
                break;
            case LocaleUtil.en:
            default:
                weeks = new String[]{"Sun.", "Mon.", "Tues.", "Wed.", "Thur.", "Fri.", "Sat."};
                break;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

}
