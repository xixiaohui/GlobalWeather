package com.xixiaohui.weather.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xixiaohui.weather.MyApplication
import com.xixiaohui.weather.view.adapter.SearchAdapter
import com.xixiaohui.weather.bean.CityBean
import com.xixiaohui.weather.data.City
import com.xixiaohui.weather.databinding.ActivityDiscoverBinding
import java.io.Serializable

class DiscoverActivity : AppCompatActivity() {
    lateinit var binding:ActivityDiscoverBinding

    lateinit var data:MutableList<CityBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val bundle = intent.getBundleExtra("DATA")
        val data =  bundle.getSerializable("DATA")

        val searchAdapter =
            SearchAdapter(data as MutableList<City>)
        val viewManager = LinearLayoutManager(MyApplication.getContext())
        viewManager.orientation = LinearLayoutManager.VERTICAL

        binding.discoverRecycleView.apply {
            adapter = searchAdapter
            layoutManager = viewManager
            setHasFixedSize(true)
        }

        binding.discoverRecycleView.addOnItemClickListener(object:OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val lat = data[position].lat
                val lon = data[position].lon

                val intent = Intent(this@DiscoverActivity, MainActivity::class.java)
                intent.putExtra("LAT",lat)
                intent.putExtra("LON",lon)
                startActivity(intent)

            }

        })




    }


    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener {
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