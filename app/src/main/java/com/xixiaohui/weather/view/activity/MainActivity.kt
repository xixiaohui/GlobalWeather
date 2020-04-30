package com.xixiaohui.weather.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xixiaohui.weather.MyApplication
import com.xixiaohui.weather.R
import com.xixiaohui.weather.databinding.ActivityMainBinding
import com.xixiaohui.weather.globalweather.until.ContentUtil
import com.xixiaohui.weather.service.LocationService
import com.xixiaohui.weather.utils.MyGetWeater
import com.xixiaohui.weather.utils.SpUtils
import com.xixiaohui.weather.utils.SpUtils.getBean
import com.xixiaohui.weather.view.fragments.ARG_BASE
import com.xixiaohui.weather.view.fragments.ARG_FORECAST
import com.xixiaohui.weather.view.fragments.ARG_NOW
import com.xixiaohui.weather.view.fragments.ScreenSlideFragment
import interfaces.heweather.com.interfacesmodule.bean.Code
import interfaces.heweather.com.interfacesmodule.bean.Lang
import interfaces.heweather.com.interfacesmodule.bean.air.Air
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow
import interfaces.heweather.com.interfacesmodule.bean.basic.Basic
import interfaces.heweather.com.interfacesmodule.bean.grid.now.GridNow
import interfaces.heweather.com.interfacesmodule.bean.search.Search
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.Forecast
import interfaces.heweather.com.interfacesmodule.bean.weather.hourly.Hourly
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase
import interfaces.heweather.com.interfacesmodule.view.HeWeather
import java.io.Serializable
import java.lang.reflect.Type
import com.xixiaohui.weather.data.Base as MyBase
import com.xixiaohui.weather.data.City as MyCity
import com.xixiaohui.weather.data.Forecast as MyForecast
import com.xixiaohui.weather.data.Now as MyNow

enum class Key {
    NOW, BASE, DAILY_FORECAST, LIFE_STYLE
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val REQUEST_PERMISSION_LOCATION = 10

    lateinit var mPager: ViewPager
    private lateinit var mPagerAdaper: ScreenSlidePagerAdapter
    var otherAreas: MutableList<ScreenSlideFragment> = mutableListOf()

    lateinit var mSearchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPermission()

        if (MyApplication.isFirst) {
//            if(SpUtils.isHaveData(MyApplication.getContext(),Key.NOW.toString())){
//                MyApplication.getMySharedPreferences()
//                bindPageView()
//                setPageViewAdaper()
//            }else{
            initLocation()
//            }
            MyApplication.isFirst = false
        } else {
            bindPageView()
            setPageViewAdaper()
            hideLoadingWidget()
        }
    }

    /**
     * 隐藏loading
     */
    fun hideLoadingWidget(): Unit {
        binding.loading.containerProgressBarLayout.visibility = View.GONE
    }

    /**
     * 显示loading
     */
    fun showLoadingWidget(): Unit {
        binding.loading.containerProgressBarLayout.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = menuInflater
        inflater.inflate(R.menu.main_activity_actions, menu)

        //获取SearchView对象
        val searchItem = menu!!.findItem(R.id.app_bar_search)
        mSearchView = searchItem.actionView as SearchView

        //设置相应的监听，文字变化的监听
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //文字提交的时候哦回调，newText是最后提交搜索的文字
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("onQueryTextSubmit", query)
                getSearchResult(query, Lang.ENGLISH)
                showLoadingWidget()
                return false
            }

            //在文字改变的时候回调，query是改变之后的文字
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("onQueryTextChange", newText)

                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 获取搜索结果
     */
    private fun getSearchResult(location: String?, lang: Lang) {
        if (location == null || location == "") {
            return
        }

        HeWeather.getSearch(
            this,
            location,
            "cn,overseas",
            20,
            lang,
            object : HeWeather.OnResultSearchBeansListener {
                override fun onSuccess(search: Search?) {

                    if(search?.status == "unknown location"){
                        Toast.makeText(this@MainActivity,"unknown location",Toast.LENGTH_SHORT).show()
                        hideLoadingWidget()
                        return
                    }
                    if (search?.status != "unknown city" && search?.getStatus() != "noData" &&search?.status != "unknown location") {

                        Log.i("getSearchResult", Gson().toJson(search))


                        val basic: MutableList<Basic>? = search?.basic!!
                        val data: MutableList<MyCity> = mutableListOf()
                        if (basic != null && basic.size > 0) {
                            if (data.size > 0) {
                                data.clear()
                            }
                            for (i in basic.indices) {
                                val mycity = MyCity(basic[i].admin_area)
                                mycity.location = basic[i].location
                                mycity.cid = basic[i].cid
                                mycity.cnty = basic[i].cnty
                                mycity.lat = basic[i].lat
                                mycity.lon = basic[i].lon
                                mycity.tz = basic[i].tz
                                mycity.parent_city = basic[i].parent_city
                                data.add(mycity)
                            }
                            val intent = Intent(this@MainActivity, DiscoverActivity::class.java)
                            var bundle = Bundle()
                            bundle.putSerializable("DATA", data as Serializable)
                            intent.putExtra("DATA", bundle)
                            startActivity(intent)
                            hideLoadingWidget()

                        }
                    }
                }

                override fun onError(p0: Throwable?) {
                    val search = Search()
                    search.status = "noData"
                }

            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_search -> {
                val intent = Intent(this@MainActivity, DiscoverActivity::class.java)
                startActivity(intent)
                true
            }
//            R.id.media_route_menu_item -> {
//                val intent = Intent(this@MainActivity, SettingActivity::class.java)
//                startActivity(intent)
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 发送数据并生成一个fragment
     */
    fun addAnotherArea(now: MyNow, base: MyBase?, forecast: MutableList<MyForecast>?): Unit {
        val fragment = ScreenSlideFragment()
        val bundle = Bundle().also {
            it.putSerializable(ARG_NOW, now)
            it.putSerializable(ARG_BASE, base)
            var i = 0
            if (forecast != null) {
                for (i in 0 until forecast!!.size) {
                    it.putSerializable(ARG_FORECAST + i, forecast[i])
                }
            }
        }
        fragment.arguments = bundle
        otherAreas.add(fragment)
    }

    /**
     * 添加区域数据
     */
    fun bindPageView(): Unit {
        val nowType: Type =
            object : TypeToken<MutableList<MyNow>>() {}.getType()
        val now: MutableList<MyNow>? =
            SpUtils.getBean(MyApplication.getContext(), Key.NOW.toString(), nowType)

        val baseType: Type =
            object : TypeToken<MutableList<MyBase>>() {}.getType()
        val base: MutableList<MyBase>? =
            SpUtils.getBean(MyApplication.getContext(), Key.BASE.toString(), baseType)

        val founderListType: Type =
            object : TypeToken<MutableList<MutableList<MyForecast>>>() {}.getType()
        val forecast: MutableList<MutableList<MyForecast>>? = SpUtils.getBean(
            MyApplication.getContext(),
            Key.DAILY_FORECAST.toString(), founderListType
        )

        for (i in now!!.indices.reversed()) {
            addAnotherArea(now[i], base!![i], forecast!![i])
        }

    }

    /**
     * 设置viewPager的适配器
     * now:新的区域 当前天气
     * base:新的区域 基础信息
     * forecast:新的区域 预报信息当天，明天，后天 共三天
     */
    fun setPageViewAdaper() {
        mPager = binding.viewPager
        mPagerAdaper = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = mPagerAdaper
//        mPager.setPageTransformer(
//            true,
//            ZoomOutPageTransformer()
//        )
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    fun getLocation(): String {
        return "蜀山"
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(
            fm,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
        var otherAreasAdapter: MutableList<ScreenSlideFragment> = mutableListOf()

        init {
            this.otherAreasAdapter = otherAreas

        }

        override fun getItem(position: Int): Fragment {
            return this.otherAreasAdapter[position]
        }

        override fun getCount(): Int {
            return otherAreasAdapter.size
        }
    }

    /**
     *
     */
    fun initData(): Unit {
//        var name = MyApplication.getContext().packageName;
//        Log.i("name", name)
        initPermissionAndLocation()
    }

    /**
     * 获取字体
     */
    fun getMyFonts(): Typeface {
//        var mgr = assets
        var tf: Typeface = Typeface.createFromAsset(assets, "fonts/ZCOOLQingKeHuangYou-Regular.ttf")
        return tf
    }

    /**
     * 获取英文字体
     */
//    fun getEnglishFonts(): Typeface {
////        var mgr = assets
//        var tf: Typeface = Typeface.createFromAsset(assets, "fonts/GloriaHallelujah-Regular.ttf")
//        return tf
//    }
//
//    fun getEnglishFontsOne(): Typeface {
////        var mgr = assets
//        var tf: Typeface = Typeface.createFromAsset(assets, "fonts/AmaticSC-Regular.ttf")
//        return tf
//    }
//
//    fun getEnglishFontsTwo(): Typeface {
////        var mgr = assets
//        var tf: Typeface =
//            Typeface.createFromAsset(assets, "fonts/MountainsofChristmas-Regular.ttf")
//        return tf
//    }
//
//    fun getEnglishFontsThree(): Typeface {
////        var mgr = assets
//        var tf: Typeface = Typeface.createFromAsset(assets, "fonts/SueEllenFrancisco-Regular.ttf")
//        return tf
//    }

    companion object {
        fun getMyFonts(): Typeface {
//        var mgr = assets
            var tf: Typeface = Typeface.createFromAsset(
                MyApplication.getContext().assets,
                "fonts/ZCOOLQingKeHuangYou-Regular.ttf"
            )
            return tf
        }

        fun getEnglishFontsOne(): Typeface {
//        var mgr = assets
            var tf: Typeface = Typeface.createFromAsset(
                MyApplication.getContext().assets,
                "fonts/AmaticSC-Regular.ttf"
            )
            return tf
        }
    }

    /**
     * 初始化 权限授权
     * 获取当前位置
     */
    fun initPermissionAndLocation() {


        initLocation()

    }

    /**
     * 和风天气API 测试
     */
    fun testHeWeatherApi(): Unit {
//        getWeather()
//        getWeatherNow()
//        getWeatherNow(Lang.FRENCH)
//        getWeatherForcast()
//        getWeatherHourly()
//        getWeatherLifeStyle()
    }

    //空气质量实况 permission denied
    fun getWeatherAirNow(): Unit {
        HeWeather.getAirNow(this@MainActivity,
            ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT,
            Lang.CHINESE_SIMPLIFIED,
            interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
            object : HeWeather.OnResultAirNowBeansListener {
                override fun onSuccess(search: AirNow?) {
                    Log.i("getWeatherAirNow", "getWeatherAirNow onSuccess:" + Gson().toJson(search))
                }

                override fun onError(e: Throwable?) {
                    Log.i("getWeatherAirNow", "getWeatherAirNow Failed:" + e)
                }
            })
    }

    //空气质量实况 permission denied
    fun getWeatherAir(): Unit {
        HeWeather.getAir(this@MainActivity,
            ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT,
            Lang.CHINESE_SIMPLIFIED,
            interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
            object : HeWeather.OnResultAirBeanListener {
                override fun onSuccess(search: Air?) {
                    Log.i("getWeatherAir", "getWeatherAir onSuccess:" + Gson().toJson(search))
                }

                override fun onError(e: Throwable?) {
                    Log.i("getWeatherAir", "getWeatherAir Failed:" + e)
                }
            })
    }

    //格点实况天气 账号没有权限 permission denied
    fun getWeatherGridNow(): Unit {
        HeWeather.getWeatherGridNow(this@MainActivity,
            ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT,
            Lang.CHINESE_SIMPLIFIED,
            interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
            object : HeWeather.OnResultWeatherGirdNowBeanListener {
                override fun onSuccess(search: GridNow?) {
                    Log.i(
                        "getWeatherGridNow",
                        "getWeatherGridNow onSuccess:" + Gson().toJson(search)
                    )
                }

                override fun onError(e: Throwable?) {
                    Log.i("getWeatherGridNow", "getWeatherGridNow Failed:" + e)
                }
            })
    }

    //生活指数
    fun getWeatherLifeStyle(): Unit {
        HeWeather.getWeatherLifeStyle(this@MainActivity,
            ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT,
            Lang.CHINESE_SIMPLIFIED,
            interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
            object : HeWeather.OnResultWeatherLifeStyleBeanListener {

                override fun onSuccess(search: Lifestyle?) {
                    Log.i(
                        "getWeatherLifeStyle",
                        "getWeatherLifeStyle onSuccess:" + Gson().toJson(search)
                    )
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
            object : HeWeather.OnResultWeatherHourlyBeanListener {

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
            object : HeWeather.OnResultWeatherForecastBeanListener {
                override fun onSuccess(search: Forecast?) {

                    var forecastJson = Gson().toJson(search?.daily_forecast)

                    val founderListType: Type =
                        object : TypeToken<MutableList<MyForecast?>?>() {}.getType()

                    var forecast: MutableList<MyForecast> =
                        Gson().fromJson<MutableList<MyForecast>>(
                            forecastJson,
                            founderListType
                        )

//                    MyApplication.forecastDatas.add(forecast)

                    Log.i(
                        "getWeatherForcast",
                        "getWeatherForcast onSuccess:" + Gson().toJson(search)
                    )
                    if (Code.OK.code
                            .equals(search!!.status, ignoreCase = true)
                    ) {
                        //设置listview


                    } else {
                        //在此查看返回数据失败的原因
                        val status = search!!.status
                        val code =
                            Code.toEnum(status)
                        Log.i("getWeatherForcast_f", "failed code: $code")
                    }
                }

                override fun onError(e: Throwable?) {
                    Log.i("getWeatherForcast", "getWeatherForcast Failed:" + e)
                }
            })
    }

    /***
     * lang 语言
     * 实况天气
     */
    fun getWeatherNow(lang: Lang = Lang.CHINESE_SIMPLIFIED): Unit {
        HeWeather.getWeatherNow(this@MainActivity,
            ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT,
            lang,
            interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
            object : HeWeather.OnResultWeatherNowBeanListener {
                override fun onSuccess(search: Now?) {
//                    Log.i("getWeatherNow", "Weather Now onSuccess:" + Gson().toJson(search))
//                    var nowJson = Gson().toJson(search?.now)
//                    //把json对象映射成Base对象
//                    var now: MyNow = Gson().fromJson<MyNow>(nowJson, MyNow::class.java)
//
//                    MyApplication.nowDatas.add(now)
//
//
//                    var baseJson = Gson().toJson(search?.basic)
//                    //把json对象映射成Base对象
//                    var base: MyBase = Gson().fromJson<MyBase>(baseJson, MyBase::class.java)
//
//                    MyApplication.baseDatas.add(base)


                    if (Code.OK.code
                            .equals(search!!.status, ignoreCase = true)
                    ) {
                        //此时返回数据
                        val now: NowBase? = search!!.now
                        getWeatherForcast()
                    } else {
                        //在此查看返回数据失败的原因
                        val status = search!!.status
                        val code =
                            Code.toEnum(status)
                        Log.i("getWeatherNow failed", "failed code: $code")
                    }

                }

                override fun onError(e: Throwable?) {
                    Log.i("onError", "Weather Now onError: ", e);
                }

            })
    }

    /**
     * 常规天气数据集合
     */
    fun getWeather(lang: Lang = Lang.CHINESE_SIMPLIFIED): Unit {
        HeWeather.getWeather(this@MainActivity,
            ContentUtil.NOW_LON.toString() + "," + ContentUtil.NOW_LAT,
            lang,
            interfaces.heweather.com.interfacesmodule.bean.Unit.METRIC,
            object : HeWeather.OnResultWeatherDataListBeansListener {
                override fun onSuccess(search: Weather?) {
//                    Log.i("getWeather", "getWeather onSuccess:" + Gson().toJson(search))
//
//                    //now
//                    var nowJson = Gson().toJson(search?.now)
//                    var now: MyNow = Gson().fromJson<MyNow>(nowJson, MyNow::class.java)
//                    MyApplication.nowDatas.add(now)
//
//                    //base
//                    var baseJson = Gson().toJson(search?.basic)
//                    var base: MyBase = Gson().fromJson<MyBase>(baseJson, MyBase::class.java)
//                    MyApplication.baseDatas.add(base)
//
//                    //forecast
//                    var forecastJson = Gson().toJson(search?.daily_forecast)
//                    val founderListType: Type =
//                        object : TypeToken<MutableList<MyForecast?>?>() {}.getType()
//                    var forecast: MutableList<MyForecast> =
//                        Gson().fromJson<MutableList<MyForecast>>(
//                            forecastJson,
//                            founderListType
//                        )
//                    MyApplication.forecastDatas.add(forecast)
//
//                    //lifestyle
//                    var lifestyleJson = Gson().toJson(search?.lifestyle)
//                    val lifestyleType: Type =
//                        object : TypeToken<MutableList<LifeStyle>>() {}.getType()
//                    var lifestyle: MutableList<LifeStyle> =
//                        Gson().fromJson<MutableList<LifeStyle>>(
//                            lifestyleJson,
//                            lifestyleType
//                        )
//                    MyApplication.lifeStyleDatas.add(lifestyle)
                }

                override fun onError(e: Throwable?) {

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
                    //可在其中解析amapLocation获取相应内容。获取经纬度，以供和风天气使用
                    ContentUtil.NOW_LON = amapLocation.longitude
                    ContentUtil.NOW_LAT = amapLocation.latitude

//                    getWeatherNow()
                    MyGetWeater.getWeather(wellDone = object : MyGetWeater.WellDone {
                        override fun getDataOk(): Boolean {
                            Log.i("onLocationChanged", "get weather success")

                            bindPageView()
                            setPageViewAdaper()
                            hideLoadingWidget()
                            return true
                        }
                    })

                    Toast.makeText(
                        this@MainActivity,
                        "Location Success" + "Lon=" + ContentUtil.NOW_LON + "Lat=" + ContentUtil.NOW_LAT,
                        Toast.LENGTH_SHORT
                    ).show()
//                    Log.i(
//                        "AmapError",
//                        "Location Success" + "经度=" + ContentUtil.NOW_LON + "纬度=" + ContentUtil.NOW_LAT
//                    );

                    mLocationClient?.onDestroy()
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e(
                        "AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo()
                    );

//                    Toast.makeText(
//                        this@MainActivity, "location Error, ErrCode:"
//                                + amapLocation.getErrorCode() + ", errInfo:"
//                                + amapLocation.getErrorInfo(), Toast.LENGTH_LONG
//                    ).show()
                }
            }
        }
    }

    /**
     * 初始化高德定位
     */
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


