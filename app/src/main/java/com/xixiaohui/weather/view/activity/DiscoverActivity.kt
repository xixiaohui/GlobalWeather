package com.xixiaohui.weather.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.NativeAdOptions
import com.xixiaohui.weather.MyApplication
import com.xixiaohui.weather.R
import com.xixiaohui.weather.bean.CityBean
import com.xixiaohui.weather.data.City
import com.xixiaohui.weather.databinding.ActivityDiscoverBinding
import com.xixiaohui.weather.utils.LocaleUtil
import com.xixiaohui.weather.utils.MyGetWeater
import com.xixiaohui.weather.view.adapter.SearchAdapter
import com.xixiaohui.weather.view.adapter.SearchHeaderAdapter
import java.io.Serializable

import com.google.android.ads.nativetemplates.TemplateView as MyNativeAdsTemplatesView

class DiscoverActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiscoverBinding

    lateinit var data: MutableList<CityBean>

    lateinit var adLoader: AdLoader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiscoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recycleViewItemOnclick()
        addNativeAds()

    }

    /**
     * adMob native ads
     */
    fun addNativeAds() {
        MobileAds.initialize(this) {}
        //test native ads id ： ca-app-pub-3940256099942544/2247696110
        var myNativeAdsId = "ca-app-pub-1941973989297560/1066029574"
        adLoader = AdLoader.Builder(this, myNativeAdsId)
            .forUnifiedNativeAd {
                //Show the ad.
                val style = NativeTemplateStyle.Builder().build()
                val template =
                    binding.discoverRecycleView.findViewById<MyNativeAdsTemplatesView>(R.id.my_native_ads_template)
                template.setStyles(style)
                template.setNativeAd(it)

                if (adLoader.isLoading) {
                    // The AdLoader is still loading ads.
                    // Expect more adLoaded or onAdFailedToLoad callbacks.
                } else {
                    // The AdLoader has finished loading ads.

                    Log.i("forUnifiedNativeAd", "success")
                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {
                    // Handle the failure by logging, altering the UI, and so on.
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()
        adLoader.loadAds(AdRequest.Builder().build(), 5)
//        adLoader.loadAd(AdRequest.Builder().build())
    }

    //初始化recycle view 并且 响应recycle view的点击事件
    fun recycleViewItemOnclick(): Unit {

        val intent = intent
        val bundle = intent.getBundleExtra("DATA")
        val data = bundle.getSerializable("DATA")

        val searchAdapter =
            SearchHeaderAdapter(data as MutableList<City>)
        val viewManager = LinearLayoutManager(MyApplication.getContext())
        viewManager.orientation = LinearLayoutManager.VERTICAL

        binding.discoverRecycleView.apply {
            adapter = searchAdapter
            layoutManager = viewManager
            setHasFixedSize(true)
        }

        binding.discoverRecycleView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val lat = data[position].lat
                val lon = data[position].lon

                //获取天气数据
                MyGetWeater.getWeather(lon, lat, LocaleUtil.getLangByLocale(),wellDone = object : MyGetWeater.WellDone {
                    override fun getDataOk(): Boolean {

                        val intent = Intent(this@DiscoverActivity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                        return true
                    }
                })

            }

        })
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    private fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                view?.setOnClickListener(null)
            }

            override fun onChildViewAttachedToWindow(view: View) {
                view?.setOnClickListener {
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                }
            }
        })
    }


}