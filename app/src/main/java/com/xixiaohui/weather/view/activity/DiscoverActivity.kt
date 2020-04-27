package com.xixiaohui.weather.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xixiaohui.weather.databinding.ActivityDiscoverBinding

class DiscoverActivity : AppCompatActivity() {
    lateinit var binding:ActivityDiscoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}