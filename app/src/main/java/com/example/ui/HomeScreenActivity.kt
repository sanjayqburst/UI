package com.example.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.example.ui.accounts.UserSharedPreference
import com.example.ui.adapters.ViewPagerAdapter
import com.example.ui.databinding.ActivityHomeScreenBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var homeScreenBinding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreference=UserSharedPreference(this)
        val tabIcons= arrayOf(R.drawable.news,R.drawable.favorite,R.drawable.profile)

        super.onCreate(savedInstanceState)

        homeScreenBinding= ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(homeScreenBinding.root)


        homeScreenBinding.apply {
            viewPager.adapter=ViewPagerAdapter(tabLayout.tabCount,supportFragmentManager,lifecycle)
            TabLayoutMediator(tabLayout,viewPager){
                tab,position->
                tab.icon=AppCompatResources.getDrawable(this@HomeScreenActivity,tabIcons[position])
            }.attach()
        }



    }
}