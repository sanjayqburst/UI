package com.example.newsapp.ui.favorites

import android.content.Context
import android.content.SharedPreferences
import com.example.newsapp.ui.accounts.UserSharedPreference

class FavSharedPreference(context: Context) {
    private val userSharedPreference = UserSharedPreference(context)
    private val sharedPreference: SharedPreferences = context.getSharedPreferences(
        userSharedPreference.getValue("username"),
        Context.MODE_PRIVATE
    )

    fun saveFav(key: Int, value: Boolean) {
        val editor = sharedPreference.edit()
        editor?.putBoolean(key.toString(), value)
        editor?.apply()
    }

    fun hasFav(key: Int): Boolean {
        return sharedPreference.contains(key.toString())
    }

    fun getKeys(): MutableSet<String> {
        return sharedPreference.all.keys
    }

    fun removeFav(key: Int) {
        sharedPreference.edit()?.remove(key.toString())?.apply()
    }
}