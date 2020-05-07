package com.xixiaohui.weather.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xixiaohui.weather.MyApplication
import com.xixiaohui.weather.R
import com.xixiaohui.weather.data.Base
import com.xixiaohui.weather.data.Forecast
import com.xixiaohui.weather.data.Now
import com.xixiaohui.weather.databinding.FragmentScreenSlideBinding
import com.xixiaohui.weather.utils.MyIconUtils
import com.xixiaohui.weather.utils.SpUtils
import com.xixiaohui.weather.view.activity.DetailActivity
import com.xixiaohui.weather.view.activity.DiscoverActivity
import com.xixiaohui.weather.view.activity.MainActivity
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
public const val ARG_BASE = "BASE"
public const val ARG_NOW = "NOW"
public const val ARG_FORECAST = "FORECAST"
public const val ARG_FORECAST0 = "FORECAST0"
public const val ARG_FORECAST1 = "FORECAST1"
public const val ARG_FORECAST2 = "FORECAST2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScreenSlideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScreenSlideFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var now: Now
    private lateinit var base: Base
    private lateinit var forecast: MutableList<Forecast>


    lateinit var mPager: ViewPager
    lateinit var binding: FragmentScreenSlideBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            base = it.getSerializable(ARG_BASE) as Base
            now = it.getSerializable(ARG_NOW) as Now
            forecast = mutableListOf()
            forecast.add(it.getSerializable(ARG_FORECAST0) as Forecast)
            forecast.add(it.getSerializable(ARG_FORECAST1) as Forecast)
            forecast.add(it.getSerializable(ARG_FORECAST2) as Forecast)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreenSlideBinding.inflate(layoutInflater)

        binding.now = this.now
        binding.base = this.base

        binding.location.typeface = MainActivity.getMyFonts()
        binding.temperature.typeface = MainActivity.getEnglishFontsOne()
        binding.condTxt.typeface = MainActivity.getMyFonts()

        binding.mainWeatherImg.setImageResource(MyIconUtils.getWeatherIcon(now.cond_code))

        initListView()
        setOnClickEvents()

        return binding.root
    }

    fun setOnClickEvents(): Unit {
        binding.mainWeatherImg.setOnClickListener {
            onClickGotoDetailPage(it)
        }
    }

    /**
     * 进入详情页面
     */
    fun onClickGotoDetailPage(viewItem: View): Unit {
        gotoDetailPage()
    }

    /**
     * 页面跳转
     * 传递数据
     */
    fun gotoDetailPage(): Unit {
        val intent = Intent(MyApplication.getContext(), DetailActivity::class.java)
        var bundle = Bundle()
        bundle.putSerializable("DATA", now as Serializable)
        intent.putExtra("DATA", bundle)
        startActivity(intent)
    }

    /**
     * 初始化 list view
     */
    fun initListView() {
        val viewManager = LinearLayoutManager(MyApplication.getContext())
        viewManager.orientation = LinearLayoutManager.HORIZONTAL

        this.viewManager = viewManager
        viewAdapter = MyAdapter(forecast)

        recyclerView = binding.recipeListView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            setHasFixedSize(true)
        }
    }

    class MyAdapter(var list: MutableList<Forecast>) :
        RecyclerView.Adapter<MyAdapter.ViewListViewHolder>() {

        class ViewListViewHolder(var viewItem: View) : RecyclerView.ViewHolder(viewItem) {}

        private val inflater: LayoutInflater =
            MyApplication.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewListViewHolder {
            var view = inflater.inflate(R.layout.forecast, parent, false)

            return ViewListViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewListViewHolder, position: Int) {

            holder.viewItem.findViewById<TextView>(R.id.text_weekday_tem_one).apply {
                this.text = list[position].tmp_max + "°" + "/" + list[position].tmp_min + "°"
                this.typeface = MainActivity.getEnglishFontsOne()
            }

            holder.viewItem.findViewById<TextView>(R.id.text_weekday_one).apply {
                val df = SimpleDateFormat("yyyy-MM-dd")
                val date = df.parse(list[position].date)
                this.text = SpUtils.getWeek(date, Locale.getDefault().language)
                this.typeface = MainActivity.getMyFonts()
            }

            holder.viewItem.findViewById<ImageView>(R.id.image_weekday_one).apply {
                this.setImageResource(MyIconUtils.getWeatherIcon(list[position].cond_code_d))
            }

            holder.viewItem.findViewById<TextView>(R.id.text_weekday_tem_two).apply {
                this.text = list[position].cond_txt_d
                this.typeface = MainActivity.getMyFonts()
            }
        }
    }


    fun getArgumentsTest(): Unit {
        val location = arguments?.get("LOCATION")
        Log.i("ScreenSlideFragment", "location = " + location)
        binding.location.text = location as CharSequence?
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val location = (context as MainActivity).getLocation()

        binding = FragmentScreenSlideBinding.inflate(layoutInflater)
        binding.location.text = location
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScreenSlideFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScreenSlideFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }
}

class FragmentAdapter(
    fm: FragmentManager?,
    private val datas: List<Fragment>
) :
    FragmentPagerAdapter(fm!!, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return datas.size
    }

    override fun getItem(position: Int): Fragment {
        return datas[position]
    }

}