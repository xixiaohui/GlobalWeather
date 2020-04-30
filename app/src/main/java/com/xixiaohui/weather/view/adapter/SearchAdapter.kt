package com.xixiaohui.weather.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.xixiaohui.weather.MyApplication
import com.xixiaohui.weather.R
import com.xixiaohui.weather.data.City
import com.xixiaohui.weather.view.activity.DiscoverActivity
import java.io.Serializable


class SearchAdapter(var data: MutableList<City>) : Adapter<RecyclerView.ViewHolder>() {


    private val inflater: LayoutInflater =
        MyApplication.getContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = inflater.inflate(R.layout.discover_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as MyViewHolder

        viewHolder.apply {
            tvLocation.text = data[position].location
            tvparent_city.text = data[position].parent_city
            tvcnty.text = data[position].cnty

            tvlat.text = "Lat: "+data[position].lat
            tvlon.text = "Lon: "+data[position].lon
        }

        viewHolder.city.setOnClickListener{

            Log.i("position = ",data[position].location)
            val lat = data[position].lat
            val lon = data[position].lon

//            val intent = Intent(actvity, DiscoverActivity::class.java)
//            intent.putExtra("LAT",lat)
//            intent.putExtra("LON",lon)

        }

    }



    internal class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val city:LinearLayout
        val tvLocation: TextView
        val tvparent_city: TextView
        val tvcnty: TextView

        val tvlat: TextView
        val tvlon: TextView

        init {
            tvLocation = itemView.findViewById(R.id.tv_item_location)
            tvparent_city = itemView.findViewById(R.id.tv_item_parent_city)
            tvcnty = itemView.findViewById(R.id.tv_item_cnty)

            tvlat = itemView.findViewById(R.id.tv_item_lat)
            tvlon = itemView.findViewById(R.id.tv_item_lon)
            city = itemView.findViewById(R.id.city)
        }
    }



}