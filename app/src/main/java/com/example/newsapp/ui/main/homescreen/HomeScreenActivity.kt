package com.example.newsapp.ui.main.homescreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeScreenBinding
import com.example.newsapp.ui.main.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var homeScreenBinding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val tabIcons = arrayOf(R.drawable.news, R.drawable.favorite, R.drawable.profile)
        super.onCreate(savedInstanceState)
        homeScreenBinding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(homeScreenBinding.root)
        homeScreenBinding.apply {
            viewPager.adapter =
                ViewPagerAdapter(tabLayout.tabCount, supportFragmentManager, lifecycle)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.icon =
                    AppCompatResources.getDrawable(this@HomeScreenActivity, tabIcons[position])
            }.attach()
        }
    }
}