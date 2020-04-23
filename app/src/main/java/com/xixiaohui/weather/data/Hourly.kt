package com.xixiaohui.weather.data

/**
 *
cloud	"96"                    云量
cond_code	"305"               天气状况代码
cond_txt	"小雨"              天气状况描述
dew	"7"                         露点温度
hum	"69"                        相对湿度
pop	"20"                        降水概率，百分比
pres	"1014"                  大气压强
time	"2020-04-17 16:00"      预报时间，格式yyyy-MM-dd hh:mm
tmp	"20"                        温度
wind_deg	"184"               风向360角度
wind_dir	"南风"              风向
wind_sc	"1-2"                   风力
wind_spd	"11"                风速，公里/小时

 */
data class Hourly(val cloud:String){


    lateinit var cond_code:String;
    lateinit var cond_txt:String;
    lateinit var dew:String;
    lateinit var hum:String;
    lateinit var pop:String;
    lateinit var pres:String;
    lateinit var time:String;
    lateinit var tmp:String;
    lateinit var wind_deg:String;
    lateinit var wind_dir:String;
    lateinit var wind_sc:String;
    lateinit var wind_spd:String;

}