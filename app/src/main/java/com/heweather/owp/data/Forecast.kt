package com.heweather.owp.data

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
data class Forecast( val cond_code_d:String){


}