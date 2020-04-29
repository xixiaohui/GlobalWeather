package com.xixiaohui.weather.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xixiaohui.weather.MyApplication
import com.xixiaohui.weather.data.Base
import com.xixiaohui.weather.data.Forecast
import com.xixiaohui.weather.data.LifeStyle
import com.xixiaohui.weather.data.Now
import com.xixiaohui.weather.globalweather.until.ContentUtil
import interfaces.heweather.com.interfacesmodule.bean.Code
import interfaces.heweather.com.interfacesmodule.bean.Lang
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase
import interfaces.heweather.com.interfacesmodule.view.HeWeather
import java.lang.reflect.Type

class MyGetWeater {

    interface WellDone{
        fun getDataOk(): Boolean
        fun getDataFail():Boolean{
            return false
        }
    }

    companion object {

        /**
         * 常规天气数据集合
         */
        fun getWeather(lon: String, lat: String, lang: Lang = Lang.CHINESE_SIMPLIFIED,wellDone:WellDone): Unit {

            var location = lon + "," + lat

            HeWeather.getWeather(MyApplication.getContext(),
                location,
                lang,
                interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
                object : HeWeather.OnResultWeatherDataListBeansListener {
                    override fun onSuccess(search: Weather?) {
                        Log.i("getWeather", "getWeather onSuccess:" + Gson().toJson(search))

                        //now
//                        var nowJson = Gson().toJson(search?.now)

//                        var now: Now = Gson().fromJson<Now>(nowJson, Now::class.java)


                        //base
//                        var baseJson = Gson().toJson(search?.basic)
//                        var base: Base = Gson().fromJson<Base>(baseJson, Base::class.java)


                        //forecast
//                        var forecastJson = Gson().toJson(search?.daily_forecast)
//                        val founderListType: Type =
//                            object : TypeToken<MutableList<Forecast?>?>() {}.getType()
//                        var forecast: MutableList<Forecast> =
//                            Gson().fromJson<MutableList<Forecast>>(
//                                forecastJson,
//                                founderListType
//                            )


                        //lifestyle
//                        var lifestyleJson = Gson().toJson(search?.lifestyle)
//                        val lifestyleType: Type =
//                            object : TypeToken<MutableList<LifeStyle>>() {}.getType()
//                        var lifestyle: MutableList<LifeStyle> =
//                            Gson().fromJson<MutableList<LifeStyle>>(
//                                lifestyleJson,
//                                lifestyleType
//                            )

                        MyApplication.nowDatas.add(search!!.now)
                        MyApplication.baseDatas.add(search!!.basic)
                        MyApplication.forecastDatas.add(search!!.daily_forecast)
                        MyApplication.lifeStyleDatas.add(search!!.lifestyle)

                        if (Code.OK.code
                                .equals(search!!.status, ignoreCase = true)
                        ) {
                            //此时返回数据
                            SpUtils.saveBean(MyApplication.getContext(),"now",MyApplication.nowDatas)
                            SpUtils.saveBean(MyApplication.getContext(),"base",MyApplication.baseDatas)
                            SpUtils.saveBean(MyApplication.getContext(),"daily_forecast",MyApplication.forecastDatas)
                            SpUtils.saveBean(MyApplication.getContext(),"lifestyle",MyApplication.lifeStyleDatas)

                            wellDone.getDataOk()
                        } else {
                            //在此查看返回数据失败的原因
                            val status = search!!.status
                            val code =
                                Code.toEnum(status)
                            Log.i("getWeatherNow failed", "failed code: $code")
                        }
                    }

                    override fun onError(e: Throwable?) {

                    }

                })
        }

        /**
         * 常规天气数据集合
         */
        fun getWeather(lang: Lang = Lang.CHINESE_SIMPLIFIED,wellDone: WellDone): Unit {
            getWeather(ContentUtil.NOW_LON.toString(), ContentUtil.NOW_LAT.toString(), lang,wellDone)
        }
    }
}