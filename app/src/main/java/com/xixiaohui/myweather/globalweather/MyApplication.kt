package com.xixiaohui.myweather.globalweather.com.xixiaohui.myweather.globalweather

import android.app.Application
import android.content.Context
import java.util.logging.Logger

class MyApplication : Application() {

    //获取屏幕的高，宽
    companion object{
        private  var instance: MyApplication? = null

        fun getContext(): Context {
            return instance!!.applicationContext
        }

    }

    var log: Logger? = null

    override fun onCreate() {
        super.onCreate()
        instance = this

        this.configLog()
    }

    private fun configLog(): Unit {

        log = Logger.getLogger(this.javaClass.toString())


    }








}