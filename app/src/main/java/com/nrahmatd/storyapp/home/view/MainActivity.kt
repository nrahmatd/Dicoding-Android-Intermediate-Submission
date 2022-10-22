package com.nrahmatd.storyapp.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.nrahmatd.storyapp.R
import com.nrahmatd.storyapp.baseview.BaseActivity
import com.nrahmatd.storyapp.createstories.view.CreateStoriesActivity
import com.nrahmatd.storyapp.databinding.ActivityMainBinding
import com.nrahmatd.storyapp.home.view.fragment.HomeFragment
import com.nrahmatd.storyapp.home.view.fragment.SettingsFragment
import com.sagara.klipz.baseview.BaseViewPager2Adapter

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var onPageChangeCallback: ViewPager2.OnPageChangeCallback
    private var tabSelected: Int = R.id.navigation_home

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup(savedInstanceState: Bundle?) {
        setFragment()
        initOnClick()
    }

    override fun statusBarColor(): Int = 0

    private fun setFragment() {
        val customerHomeBinding = HomeFragment()
        val transactionBinding = SettingsFragment()

        val fragment = ArrayList<Fragment>()
        fragment.add(customerHomeBinding)
        fragment.add(transactionBinding)

        binding.viewPagerCustomer.adapter = BaseViewPager2Adapter(this, fragment)
        binding.viewPagerCustomer.offscreenPageLimit = fragment.size
        binding.viewPagerCustomer.isUserInputEnabled = false

        onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setActive(position)
                super.onPageSelected(position)
            }
        }
        binding.viewPagerCustomer.registerOnPageChangeCallback(onPageChangeCallback)
        binding.bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> binding.viewPagerCustomer.currentItem = 0
                R.id.navigation_setting -> binding.viewPagerCustomer.currentItem = 1
            }
            true
        }

        binding.bottomNavView.selectedItemId = tabSelected
    }

    private fun initOnClick() {
        binding.ivAddHome.setOnClickListener {
            startActivity(Intent(this, CreateStoriesActivity::class.java))
        }
    }

    private fun setActive(pos: Int) {
        when (pos) {
            0 -> binding.bottomNavView.selectedItemId = R.id.navigation_home
            1 -> binding.bottomNavView.selectedItemId = R.id.navigation_setting
        }
    }
}
