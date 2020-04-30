package com.xixiaohui.weather.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xixiaohui.weather.R
import com.xixiaohui.weather.data.City

class SearchHeaderAdapter(var data: MutableList<City>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_HEADER: Int = 0
    private val TYPE_LIST: Int = 1

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return TYPE_LIST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val header =
                LayoutInflater.from(parent.context).inflate(R.layout.rv_header, parent, false)
            return ViewHolderHeader(header)
        }

        val header =
            LayoutInflater.from(parent.context).inflate(R.layout.discover_list, parent, false)
        return ViewHolder(header)
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position != 0) {
            val viewHolder = holder as ViewHolder

            viewHolder.apply {
                tvLocation.text = data[position].location
                tvparent_city.text = data[position].parent_city
                tvcnty.text = data[position].cnty

                tvlat.text = "Lat: " + data[position].lat
                tvlon.text = "Lon: " + data[position].lon
            }

            viewHolder.city.setOnClickListener {

                Log.i("position = ", data[position].location)
                val lat = data[position].lat
                val lon = data[position].lon

            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val city: LinearLayout
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

    class ViewHolderHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}