package com.heweather.owp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.google.gson.Gson
import com.heweather.owp.globalweather.until.ContentUtil
import com.heweather.owp.service.LocationService
import interfaces.heweather.com.interfacesmodule.bean.Code
import interfaces.heweather.com.interfacesmodule.bean.Lang
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.Forecast
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase
import interfaces.heweather.com.interfacesmodule.view.HeWeather
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val REQUEST_PERMISSION_LOCATION = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPermission()
        initLocation()
        getWeatherNow()
        getWeatherForcast()
        getWeatherHourly()
        getWeatherLifeStyle()
    }
    //生活指数
    fun getWeatherLifeStyle(): Unit {
        HeWeather.getWeatherLifeStyle(this@MainActivity,
            ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT,
            Lang.CHINESE_SIMPLIFIED,
            interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
            object : HeWeather.OnResultWeatherLifeStyleBeanListener{

                override fun onSuccess(search: Lifestyle?) {
                    Log.i("getWeatherLifeStyle", "getWeatherLifeStyle onSuccess:" + Gson().toJson(search))
                }

                override fun onError(e: Throwable?) {
                    Log.i("getWeatherLifeStyle", "getWeatherLifeStyle Failed:" + e)
                }

            })
    }
    //逐小时预报
    fun getWeatherHourly(): Unit {
        HeWeather.getWeatherHourly(this@MainActivity,
            ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT,
            Lang.CHINESE_SIMPLIFIED,
            interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
            object : HeWeather.OnResultWeatherHourlyBeanListener{

                override fun onSuccess(search: Hourly?) {
                    Log.i("getWeatherHourly", "getWeatherHourly onSuccess:" + Gson().toJson(search))
                }

                override fun onError(e: Throwable?) {
                    Log.i("getWeatherHourly", "getWeatherHourly Failed:" + e)
                }

            })
    }
    //未来一周预报 官方说是3-10天预报
    fun getWeatherForcast(): Unit {
        HeWeather.getWeatherForecast(this@MainActivity,
            ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT,
            Lang.CHINESE_SIMPLIFIED,
            interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
            object : HeWeather.OnResultWeatherForecastBeanListener{
                override fun onSuccess(search: Forecast?) {

                    Log.i("getWeatherForcast", "getWeatherForcast onSuccess:" + Gson().toJson(search))

                }

                override fun onError(e: Throwable?) {
                    Log.i("getWeatherForcast", "getWeatherForcast Failed:" + e)
                }

            })
    }
    //实况天气
    fun getWeatherNow(): Unit {
        HeWeather.getWeatherNow(this@MainActivity,
            ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT,
            Lang.CHINESE_SIMPLIFIED,
            interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
            object : HeWeather.OnResultWeatherNowBeanListener {
                override fun onSuccess(search: Now?) {
                    Log.i("OnSuccess", "Weather Now onSuccess:" + Gson().toJson(search))

                    Toast.makeText(this@MainActivity, search?.basic?.location, Toast.LENGTH_LONG)
                        .show()
                    location.text = search?.basic?.location

                    if (Code.OK.code
                            .equals(search!!.status, ignoreCase = true)
                    ) {
                        //此时返回数据
                        val now: NowBase = search!!.now

                    } else {
                        //在此查看返回数据失败的原因
                        val status = search!!.status
                        val code =
                            Code.toEnum(status)
                        Log.i("OnSuccess failed", "failed code: $code")
                    }

                }

                override fun onError(e: Throwable?) {
                    Log.i("onError", "Weather Now onError: ", e);
                }

            })
    }

    //声明AMapLocationClient类对象
    var mLocationClient: AMapLocationClient? = null

    //声明定位回调监听器
    var mLocationListener = object : AMapLocationListener {
        override fun onLocationChanged(amapLocation: AMapLocation?) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。

                    ContentUtil.NOW_LON = amapLocation.longitude
                    ContentUtil.NOW_LAT = amapLocation.latitude

                    Toast.makeText(
                        this@MainActivity,
                        "Location Success" + "经度=" + ContentUtil.NOW_LON + "纬度=" + ContentUtil.NOW_LAT,
                        Toast.LENGTH_LONG
                    ).show()
                    Log.i(
                        "AmapError",
                        "Location Success" + "经度=" + ContentUtil.NOW_LON + "纬度=" + ContentUtil.NOW_LAT
                    );

                    mLocationClient?.onDestroy()
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e(
                        "AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo()
                    );

                    Toast.makeText(
                        this@MainActivity, "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo(), Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    private fun initLocation() {
        //初始化定位
        mLocationClient = AMapLocationClient(applicationContext)
        //设置定位回调监听
        //声明AMapLocationClientOption对象
        val mLocationOption = AMapLocationClientOption()
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.interval = 10000
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.httpTimeOut = 20000
        mLocationClient?.setLocationListener(mLocationListener)
        //给定位客户端对象设置定位参数
        mLocationClient?.setLocationOption(mLocationOption)
        //启动定位
        mLocationClient?.startLocation()
    }

    private fun initPermission() {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            !== PackageManager.PERMISSION_GRANTED
        ) {
            // 没有权限
            ActivityCompat.requestPermissions(
                this@MainActivity, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_PERMISSION_LOCATION
            )
        } else {
            startService(Intent(this, LocationService::class.java))
            startIntent()
        }
    }

    private fun startIntent() {

    }
}