package com.xixiaohui.weather.data

import java.io.Serializable


data class Now(var cond_code:String): Serializable,WeatherData {

    //cond_code实况天气状况代码

    lateinit var cloud:String//云量
    lateinit var cond_txt:String//实况天气状况描述
    lateinit var fl:String //体感温度，默认单位：摄氏度
    lateinit var hum:String//相对湿度
    lateinit var pcpn:String//降水量
    lateinit var pres:String//大气压强
    lateinit var tmp:String//温度，默认单位：摄氏度
    lateinit var vis:String//能见度，默认单位：公里
    lateinit var wind_deg:String//风向360角度
    lateinit var wind_dir:String//风向
    lateinit var wind_sc:String//风力
    lateinit var wind_spd:String//风速，公里/小时
}