package com.xixiaohui.weather.data

import android.os.Parcelable
import java.io.Serializable

/**
 *
admin_area	"北京"
cid	"CN101010100"
cnty	"中国"
lat	"39.90498734"
location	"北京"
lon	"116.4052887"
parent_city	"北京"
tz	"+8.00"
 */

data class City(var admin_area:String) :Serializable{
    lateinit var cid:String;//	"CN101010100"
    lateinit var cnty:String;//	"中国"
    lateinit var lat:String;//	"39.90498734"
    lateinit var location:String;//	"北京"
    lateinit var lon:String;//	"116.4052887"
    lateinit var parent_city:String;//	"北京"
    lateinit var tz:String;//	"+8.00"
}