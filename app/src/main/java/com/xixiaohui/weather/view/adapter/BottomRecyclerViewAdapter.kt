package com.xixiaohui.weather.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xixiaohui.weather.MyApplication
import com.xixiaohui.weather.R
import com.xixiaohui.weather.data.Now
import com.xixiaohui.weather.utils.TextItemViewHolder
import com.xixiaohui.weather.view.activity.MainActivity


class BottomRecyclerViewAdapter() : RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<Now>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.forcast_item_view, parent, false) as View
        return TextItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        var textView = holder.myView.findViewById<TextView>(R.id.now_tmp_value)
        textView.text = item.tmp
        textView.typeface = MainActivity.getMyFonts()

        textView = holder.myView.findViewById<TextView>(R.id.now_fl_value)
        textView.text = item.fl
        textView.typeface = MainActivity.getMyFonts()

        textView = holder.myView.findViewById<TextView>(R.id.now_pcpn_value)
        textView.text = item.pcpn
        textView.typeface = MainActivity.getMyFonts()

        textView = holder.myView.findViewById<TextView>(R.id.now_pres_value)
        textView.text = item.pres
        textView.typeface = MainActivity.getMyFonts()

        textView = holder.myView.findViewById<TextView>(R.id.now_vis_value)
        textView.text = item.vis
        textView.typeface = MainActivity.getMyFonts()

        textView = holder.myView.findViewById<TextView>(R.id.now_wind_dir_value)
        textView.text = item.wind_dir
        textView.typeface = MainActivity.getMyFonts()

//        textView = holder.myView.findViewById<TextView>(R.id.now_tmp)
//        textView.typeface = MainActivity.getMyFonts()
//        textView = holder.myView.findViewById<TextView>(R.id.now_fl)
//        textView.typeface = MainActivity.getMyFonts()
//        textView = holder.myView.findViewById<TextView>(R.id.now_pcpn)
//        textView.typeface = MainActivity.getMyFonts()
//        textView = holder.myView.findViewById<TextView>(R.id.now_pres)
//        textView.typeface = MainActivity.getMyFonts()
//        textView = holder.myView.findViewById<TextView>(R.id.now_vis)
//        textView.typeface = MainActivity.getMyFonts()
//        textView = holder.myView.findViewById<TextView>(R.id.now_wind_dir)
//        textView.typeface = MainActivity.getMyFonts()
    }


}