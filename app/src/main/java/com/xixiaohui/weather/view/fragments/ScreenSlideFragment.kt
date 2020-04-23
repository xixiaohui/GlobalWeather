package com.xixiaohui.weather.view.fragments

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.xixiaohui.weather.R
import com.xixiaohui.weather.data.Base
import com.xixiaohui.weather.data.Now
import com.xixiaohui.weather.databinding.FragmentScreenSlideBinding
import com.xixiaohui.weather.view.activity.MainActivity
import org.apache.log4j.chainsaw.Main

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
public const val ARG_BASE = "BASE"
public const val ARG_NOW = "NOW"

/**
 * A simple [Fragment] subclass.
 * Use the [ScreenSlideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScreenSlideFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var now: Now? = null
    private var base: Base? = null

    lateinit var mPager:ViewPager

    lateinit var binding:FragmentScreenSlideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            base = it.getSerializable(ARG_BASE) as Base
            now = it.getSerializable(ARG_NOW) as Now
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreenSlideBinding.inflate(layoutInflater)

        binding.now = this.now
        binding.base = this.base

        binding.location.typeface = (activity as MainActivity).getMyFonts()
        binding.temperature.typeface = (activity as MainActivity).getEnglishFonts()

        // Inflate the layout for this fragment
        return binding.root
    }



    fun getArgumentsTest(): Unit {
        val location = arguments?.get("LOCATION")
        Log.i("ScreenSlideFragment","location = " + location)
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
    FragmentPagerAdapter(fm!!,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return datas.size
    }

    override fun getItem(position: Int): Fragment {
        return datas[position]
    }

}