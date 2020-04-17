package com.xixiaohui.myweather.globalweather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.xixiaohui.myweather.globalweather.service.LocationService

class SplashActivity : AppCompatActivity() {

    val REQUEST_PERMISSION_LOCATION = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initPermission()
    }

    private fun initPermission() {

        if (ContextCompat.checkSelfPermission(
                this@SplashActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this@SplashActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            !== PackageManager.PERMISSION_GRANTED
        ) {
            // 没有权限

            // 没有权限
            ActivityCompat.requestPermissions(
                this@SplashActivity, arrayOf(
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
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}