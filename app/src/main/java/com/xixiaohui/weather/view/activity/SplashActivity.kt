package com.xixiaohui.weather.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xixiaohui.weather.MyApplication
import com.xixiaohui.weather.R
import com.xixiaohui.weather.data.Now
import com.xixiaohui.weather.utils.SpUtils

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SpUtils.clearSp(MyApplication.getContext())
//        MyApplication.getMySharedPreferences()

        this.runToMainActivity()
    }

    private fun runToMainActivity(): Unit {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}