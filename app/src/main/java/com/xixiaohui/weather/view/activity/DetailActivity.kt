package com.xixiaohui.weather.view.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.xixiaohui.weather.DetailFragment
import com.xixiaohui.weather.data.Forecast
import com.xixiaohui.weather.data.Now
import com.xixiaohui.weather.data.WeatherData
import com.xixiaohui.weather.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    private lateinit var data: MutableList<Forecast>

    lateinit var mTabLayout: TabLayout
    lateinit var mViewPager: ViewPager

    val TAG = "DetailActivity"
    var titles = listOf<String>("Today","Tomorrow","Tomorrow + 1")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //设置动作栏返回按钮
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        //获取数据
        getBundleExtraData()

        initView()
    }

    fun initView() {
        mViewPager = binding.viewPager

        //设置mTabLayout
        mTabLayout = binding.myTablayout
        mTabLayout.setupWithViewPager(mViewPager,false)


        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.i("onTabReselected", tab!!.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.i("onTabSelected", tab!!.text.toString())
            }
        })


        val fragments: MutableList<Fragment> = mutableListOf()
        fragments.add(DetailFragment.newInstance(data[0]))
        fragments.add(DetailFragment.newInstance(data[1]))
        fragments.add(DetailFragment.newInstance(data[2]))

        val adapter = MyDetailPageViewAdapter(supportFragmentManager, fragments,titles)
        mViewPager.adapter = adapter

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                Log.i(TAG, "onPageSelected " + position)
            }

        })
        println()
    }

    private inner class MyDetailPageViewAdapter(
        fm: FragmentManager,
        var fragments: MutableList<Fragment>,
        titles:List<String>
    ) : FragmentStatePagerAdapter(
        fm,
        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

        var mTitles:List<String> = titles

        override fun getItem(position: Int): Fragment {
            super.getPageTitle(position)
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles.get(position)
        }

    }

    /**
     * 获取传递来的数据
     */
    fun getBundleExtraData(): Unit {
        val intent = intent
        val bundle = intent.getBundleExtra("DATA")
        val data = bundle.getSerializable("DATA")
        this.data = data as MutableList<Forecast>
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //设置动作栏返回按钮点击事件
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}