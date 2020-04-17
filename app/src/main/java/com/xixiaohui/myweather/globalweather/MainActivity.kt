package com.xixiaohui.myweather.globalweather

//import com.google.android.gms.ads.AdListener
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.MobileAds
//import com.google.android.gms.ads.RequestConfiguration

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.google.gson.Gson
import com.xixiaohui.myweather.globalweather.bean.CityBean
import com.xixiaohui.myweather.globalweather.bean.CityBeanList
import com.xixiaohui.myweather.globalweather.until.ContentUtil
import com.xixiaohui.myweather.globalweather.until.SpUtils
import interfaces.heweather.com.interfacesmodule.bean.Code
import interfaces.heweather.com.interfacesmodule.bean.Lang
import interfaces.heweather.com.interfacesmodule.bean.search.Search
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase
import interfaces.heweather.com.interfacesmodule.view.HeWeather
import interfaces.heweather.com.interfacesmodule.view.HeWeather.OnResultSearchBeansListener
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    private var fragments: MutableList<Fragment>? = null
    
    var cityBeanList: CityBeanList? = CityBeanList()
    private var locaitons: MutableList<String>? = null
    private var locaitonsEn: MutableList<String>? = null
    private var cityIds: MutableList<String>? = null

    //声明AMapLocationClient类对象
    var mLocationClient: AMapLocationClient? = null

    private val viewPager: ViewPager? = null
    private val llRound: LinearLayout? = null
    private var mNum = 0
    private val tvLocation: TextView? = null
    private val ivLoc: ImageView? = null
    private val ivBack: ImageView? = null
    private val condCode: String? = null
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
//        initFragments(true)
        getWeatherNow()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun getWeatherNow(): Unit {
        HeWeather.getWeatherNow(this@MainActivity,"CN101010100",Lang.CHINESE_SIMPLIFIED,interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
        object : HeWeather.OnResultWeatherNowBeanListener{
            override fun onSuccess(dataObject: Now?) {
                Log.i("OnSuccess","Weather Now onSuccess:" + Gson().toJson(dataObject))

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
    private fun initFragments(first: Boolean): Unit {
        cityBeanList = SpUtils.getBean(
            this@MainActivity, "cityBean", CityBeanList::class.java)


        val cityBeanEn = SpUtils.getBean(
            this@MainActivity, "cityBeanEn",
            CityBeanList::class.java
        )
        val cityBean =
            SpUtils.getBean(this@MainActivity, "cityBean", CityBeanList::class.java)
        locaitonsEn = ArrayList<String>()
        locaitons = ArrayList<String>()
        if (cityBeanEn != null) {
            for (city in cityBeanEn.cityBeans) {
                val cityName = city.cityName
                (locaitonsEn as ArrayList<String>).add(cityName)

            }
        }
        if (cityBean != null) {
            for (city in cityBean.cityBeans) {
                val cityName = city.cityName
                (locaitons as ArrayList<String>).add(cityName)
            }
        }
        cityIds = ArrayList<String>()
        fragments = ArrayList<Fragment>()
        if (first) {
            initLocation()
        } else {
            getNowCity(false)
        }
    }

    private fun getNowCity(first: Boolean) {
        val lang: Lang
        lang =
            if (ContentUtil.APP_SETTING_LANG.equals("en")
                || ContentUtil.APP_SETTING_LANG.equals("sys")
                && ContentUtil.SYS_LANG.equals(
                    "en"
                )
            ) {
                Lang.ENGLISH
            } else {
                Lang.CHINESE_SIMPLIFIED
            }
        HeWeather.getSearch(
            this,
            ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT,
            "cn,overseas",
            3,
            lang,
            object : OnResultSearchBeansListener {
                override fun onError(throwable: Throwable) {
                    val cityBeans: MutableList<CityBean?> =
                        ArrayList<CityBean?>()
                    val cityBean = CityBean()
                    cityBean.setCityName("纽约")
                    cityBean.setCityId("US3290117")
                    cityBeans.add(cityBean)
                    getData(cityBeans, first)
                }

                override fun onSuccess(search: Search) {
                    val basic =
                        search.basic[0]
                    val cid = basic.cid
                    val location = basic.location
                    if (first) {
                        ContentUtil.NOW_CITY_ID = cid
                        ContentUtil.NOW_CITY_NAME = location
                    }
                    var cityBeans: MutableList<CityBean?> =
                        ArrayList<CityBean?>()
                    val cityBean = CityBean()
                    cityBean.setCityName(location)
                    cityBean.setCityId(cid)
                    locaitons?.add(0, location)

                    locaitonsEn?.add(0, location)
                    if (cityBeanList != null
                        && cityBeanList!!.cityBeans != null
                        && cityBeanList!!.cityBeans.size > 0
                    ) {
                        cityBeans = cityBeanList!!.cityBeans
                        cityBeans.add(0, cityBean)
                    } else {
                        cityBeans.add(cityBean)
                    }
                    tvLocation?.setText(location)
                    getData(cityBeans, first)
                }
            })
    }

    /**
     * 获取数据
     */
    private fun getData(
        cityBeans: MutableList<CityBean?>,
        first: Boolean
    ) {
//        fragments = ArrayList()
//        llRound!!.removeAllViews()
//        for (city in cityBeans) {
//            val cityId = city?.cityId
//            cityIds!!.add(cityId)
//            val weatherFragment: WeatherFragment = WeatherFragment.newInstance(cityId)
//            (fragments as ArrayList<Fragment>).add(weatherFragment)
//        }
//        if (cityIds!![0].equals(ContentUtil.NOW_CITY_ID, ignoreCase = true)) {
//            ivLoc!!.visibility = View.VISIBLE
//        } else {
//            ivLoc!!.visibility = View.INVISIBLE
//        }
//        var view: View
//        for (i in (fragments as ArrayList<Fragment>).indices) {
//            //创建底部指示器(小圆点)
//            view = View(this@MainActivity)
//            view.setBackgroundResource(R.drawable.background)
//            view.isEnabled = false
//            //设置宽高
//            val layoutParams: LinearLayout.LayoutParams =
//                LinearLayout.LayoutParams(DisplayUtil.dip2px(this, 4), DisplayUtil.dip2px(this, 4))
//            //设置间隔
//            if (fragments.get(i) !== fragments.get(0)) {
//                layoutParams.leftMargin = 10
//            }
//            //添加到LinearLayout
//            llRound.addView(view, layoutParams)
//        }
//        viewPager!!.adapter = ViewPagerAdapter(supportFragmentManager, fragments)
//        //第一次显示小白点
//        llRound.getChildAt(0).isEnabled = true
//        mNum = 0
//        if (fragments.size == 1) {
//            llRound.visibility = View.GONE
//        } else {
//            llRound.visibility = View.VISIBLE
//        }
//        viewPager.addOnPageChangeListener(object : OnPageChangeListener() {
//            fun onPageScrolled(i: Int, v: Float, i1: Int) {}
//            fun onPageSelected(i: Int) {
//                if (cityIds!![i].equals(ContentUtil.NOW_CITY_ID, ignoreCase = true)) {
//                    ivLoc.visibility = View.VISIBLE
//                } else {
//                    ivLoc.visibility = View.INVISIBLE
//                }
//                llRound.getChildAt(mNum).isEnabled = false
//                llRound.getChildAt(i).isEnabled = true
//                mNum = i
//                tvLocation!!.text = locaitons!![i]
//                if (ContentUtil.SYS_LANG.equalsIgnoreCase("en")) {
//                    tvLocation.text = locaitonsEn!![i]
//                }
//            }
//
//            fun onPageScrollStateChanged(i: Int) {}
//        })
//        if (!first && fragments.size > 1) {
//            viewPager.currentItem = 1
//            getNow(cityIds!![1], false)
//        } else {
//            viewPager.currentItem = 0
//            getNow(ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT, true)
//        }
    }

    var mLocationListener =
        AMapLocationListener { aMapLocation ->
            if (aMapLocation.errorCode == 0) {
                ContentUtil.NOW_LON = aMapLocation.longitude
                ContentUtil.NOW_LAT = aMapLocation.latitude
                getNowCity(true)
                mLocationClient!!.onDestroy()
            } else {
                if ((ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                            !== PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                            !== PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.READ_PHONE_STATE
                    )
                            !== PackageManager.PERMISSION_GRANTED)
                ) {
//                    // 没有权限
//                    val view: View =
//                        LayoutInflater.from(this@MainActivity).inflate(R.layout.pop_loc_list, null)
//                    val locListWindow = LocListWindow(
//                        view,
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        this@MainActivity
//                    )
//                    locListWindow.show()
//                    locListWindow.showAtLocation(tvLocation, Gravity.CENTER, 0, 0)
//                    if (ContentUtil.FIRST_OPEN) {
//                        ContentUtil.FIRST_OPEN = false
//                        SpUtils.putBoolean(this@MainActivity, "first_open", false)
//                    }
                    Log.i("AMapLocationListener","no quan xian")
                }
                getNowCity(true)
                mLocationClient!!.onDestroy()
            }
        }

    private fun initLocation() {
        //初始化定位

        //初始化定位
        mLocationClient = AMapLocationClient(applicationContext)
        //设置定位回调监听

        //声明AMapLocationClientOption对象
        //设置定位回调监听

        //声明AMapLocationClientOption对象
        val mLocationOption = AMapLocationClientOption()
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.interval = 10000
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.httpTimeOut = 20000
        mLocationClient!!.setLocationListener(mLocationListener)
        //给定位客户端对象设置定位参数
        //给定位客户端对象设置定位参数
        mLocationClient!!.setLocationOption(mLocationOption)
        //启动定位
        //启动定位
        mLocationClient!!.startLocation()
    }


}
