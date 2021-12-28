package com.example.newsapp.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.ui.main.favorites.FavouritesFragment
import com.example.newsapp.ui.main.news.NewsFragment
import com.example.newsapp.ui.main.settings.SettingsFragment

//      Adapter for viewpager2
class ViewPagerAdapter(
    private val count: Int,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return count
    }

    //    Assigning fragments to each position respectively
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewsFragment()
            1 -> FavouritesFragment()
            2 -> SettingsFragment()
            else -> NewsFragment()
        }
    }
}