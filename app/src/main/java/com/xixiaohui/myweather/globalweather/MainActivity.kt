package com.xixiaohui.myweather.globalweather

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
//import com.google.android.gms.ads.AdListener
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.MobileAds
//import com.google.android.gms.ads.RequestConfiguration

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

//        MobileAds.initialize(this) {}
//
//        MobileAds.setRequestConfiguration(
//            RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("25BC5BA54C2FF33B54773F67AAF0A33F")).build()
//        )
//
//        val adRequest = AdRequest.Builder().build()
//
//
//        adView.loadAd(adRequest)

//        val listener:AdListener = AdListener()

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
//        adView.pause()

    }

    override fun onResume() {
        super.onResume()
//        adView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
//        adView.destroy()
    }


}
