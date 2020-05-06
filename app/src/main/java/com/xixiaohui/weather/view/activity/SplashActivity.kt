package com.xixiaohui.weather.view.activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.xixiaohui.weather.MyApplication
import com.xixiaohui.weather.R
import com.xixiaohui.weather.data.Now
import com.xixiaohui.weather.utils.LocaleUtil
import com.xixiaohui.weather.utils.LocaleUtil.getCurrentLocale
import com.xixiaohui.weather.utils.SpUtil
import com.xixiaohui.weather.utils.SpUtils
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SpUtils.clearSp(MyApplication.getContext())

        this.runToMainActivity()
    }


    private fun runToMainActivity(): Unit {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.e("TAG", "onConfigurationChanged");
        LocaleUtil.setLanguage(MyApplication.getContext(), newConfig);
    }

    fun getUserLocale(): Locale {
        val locale = LocaleUtil.getCurrentLocale(MyApplication.getContext())
        return locale
    }
}