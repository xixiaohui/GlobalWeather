package com.heweather.owp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.heweather.owp.globalweather.until.ContentUtil
import interfaces.heweather.com.interfacesmodule.bean.Code
import interfaces.heweather.com.interfacesmodule.bean.Lang
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase
import interfaces.heweather.com.interfacesmodule.view.HeConfig
import interfaces.heweather.com.interfacesmodule.view.HeWeather

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWeatherNow()
    }

    fun getWeatherNow(): Unit {
        HeWeather.getWeatherNow(this@MainActivity, "auto_ip", Lang.CHINESE_SIMPLIFIED, interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
                object : HeWeather.OnResultWeatherNowBeanListener {
                    override fun onSuccess(dataObject: Now?) {
                        Log.i("OnSuccess", "Weather Now onSuccess:" + Gson().toJson(dataObject))

                        if (Code.OK.code
                                        .equals(dataObject!!.status, ignoreCase = true)
                        ) {
                            //此时返回数据
                            val now: NowBase = dataObject!!.now

                        } else {
                            //在此查看返回数据失败的原因
                            val status = dataObject!!.status
                            val code =
                                    Code.toEnum(status)
                            Log.i("OnSuccess", "failed code: $code")
                        }

                    }

                    override fun onError(e: Throwable?) {
                        Log.i("onError", "Weather Now onError: ", e);
                    }

                })
    }
}