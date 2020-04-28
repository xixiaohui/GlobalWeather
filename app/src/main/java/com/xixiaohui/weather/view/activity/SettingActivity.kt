package com.xixiaohui.weather.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xixiaohui.weather.R
import com.xixiaohui.weather.databinding.ActivityDiscoverBinding
import com.xixiaohui.weather.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }




}