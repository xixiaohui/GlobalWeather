package com.xixiaohui.weather.data

import java.io.Serializable

/**
 *
admin_area	"安徽"
cid	"CN101220109"
cnty	"中国"
lat	"31.85586739"
location	"蜀山"
lon	"117.2620697"
parent_city	"合肥"
tz	"+8.00"
 */

data class Base(var cid:String):Serializable {
    lateinit var admin_area:String
    lateinit var cnty:String
    lateinit var lat:String
    lateinit var lon:String
    lateinit var location:String
    lateinit var parent_city:String
    lateinit var tz:String
}