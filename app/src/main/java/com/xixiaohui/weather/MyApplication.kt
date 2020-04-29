package com.xixiaohui.weather

import android.app.Application
import android.content.Context
import com.xixiaohui.weather.data.Base
import com.xixiaohui.weather.data.Forecast
import com.xixiaohui.weather.data.LifeStyle
import com.xixiaohui.weather.data.Now
import com.xixiaohui.weather.globalweather.until.ContentUtil
import com.xixiaohui.weather.view.fragments.ScreenSlideFragment
import interfaces.heweather.com.interfacesmodule.bean.basic.Basic
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.ForecastBase
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.LifestyleBase
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase
import interfaces.heweather.com.interfacesmodule.view.HeConfig
//import java.util.logging.Logger

import org.apache.log4j.Logger


class MyApplication : Application() {

    //获取屏幕的高，宽
    companion object{
        private  var instance: MyApplication? = null

        fun getContext(): Context {
            return instance!!.applicationContext
        }

        var nowDatas: MutableList<NowBase> = mutableListOf()
        var baseDatas: MutableList<Basic> = mutableListOf()
        var forecastDatas: MutableList<MutableList<ForecastBase>> = mutableListOf()
        var lifeStyleDatas: MutableList<MutableList<LifestyleBase>> = mutableListOf()
//        var nowDatas: MutableList<Now> = mutableListOf()
//        var baseDatas: MutableList<Base> = mutableListOf()
//        var forecastDatas: MutableList<MutableList<Forecast>> = mutableListOf()
//        var lifeStyleDatas: MutableList<MutableList<LifeStyle>> = mutableListOf()
        //增加额外的区域
//        var otherAreas: MutableList<ScreenSlideFragment> = mutableListOf()

        var isFirst:Boolean = true


    }

    var log: Logger? = null

    override fun onCreate() {
        super.onCreate()
        instance = this

        //在主线程中new的handler就是主线程的handler
        //初始化Handler
        HeConfig.init(ContentUtil.APK_USERNAME, ContentUtil.APK_KEY)
        HeConfig.switchToFreeServerNode()

        this.configLog()
    }

    fun configLog() {
//        try {
//            val logConfigurator = LogConfigurator()
//
//            logConfigurator.setFileName(
//                Environment.getExternalStorageDirectory()
//                    .toString() + File.separator + "crifanli_log4j.log"
//            )
//            // Set the root log level
//            logConfigurator.setRootLevel(Level.DEBUG)
//            // Set log level of a specific logger
//            logConfigurator.setLevel("org.apache", Level.ERROR)
//            logConfigurator.configure()
//            val catchHandler: CrashHandler = CrashHandler.getInstance()
//            catchHandler.init(applicationContext)
//        } catch (e: Exception) {
//            val TAG = "sky"
//            Log.i(TAG, "configLog: $e")
//        }
//
//        //gLogger = Logger.getLogger(this.getClass());
//        log = Logger.getLogger("CrifanLiLog4jTest")
    }








}