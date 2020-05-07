package com.xixiaohui.weather.data

import java.io.Serializable

/**
cond_code_d	"305"       白天天气状况代码
cond_code_n	"104"       夜间天气状况代码
cond_txt_d	"小雨"      白天天气状况描述
cond_txt_n	"阴"        晚间天气状况描述
date	"2020-04-17"    预报日期
hum	"59"                相对湿度
mr	"02:45"             月升时间
ms	"13:30"             月落时间
pcpn	"0.0"           降水量
pop	"0"                 降水概率
pres	"1014"          大气压强
sr	"05:38"             日出时间
ss	"18:42"             日落时间
tmp_max	"21"            最高温度
tmp_min	"13"            最低温度
uv_index	"2"         紫外线强度指数
vis	"25"                能见度，单位：公里
wind_deg	"20"        风向360角度
wind_dir	"东北风"    风向
wind_sc	"3-4"           风力
wind_spd	"24"        风速，公里/小时
 */
data class Forecast( val cond_code_d:String): Serializable,WeatherData {

    lateinit var cond_code_n:String;
    lateinit var cond_txt_d:String;
    lateinit var cond_txt_n:String;
    lateinit var date:String;
    lateinit var hum:String;
    lateinit var mr:String;
    lateinit var ms:String;
    lateinit var pcpn:String;
    lateinit var pop:String;
    lateinit var pres:String;//	"1014"          大气压强
    lateinit var sr:String;//	"05:38"             日出时间
    lateinit var ss:String;//	"18:42"             日落时间
    lateinit var tmp_max:String;//	"21"            最高温度
    lateinit var tmp_min:String;//	"13"            最低温度
    lateinit var uv_index:String//	"2"         紫外线强度指数
    lateinit var vis:String;//	"25"                能见度，单位：公里
    lateinit var wind_deg:String;//	"20"        风向360角度
    lateinit var wind_dir:String;//	"东北风"    风向
    lateinit var wind_sc:String;//	"3-4"           风力
    lateinit var wind_spd:String;//	"24"        风速，公里/小时

}