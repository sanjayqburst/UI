package com.example.newsapp.ui.main.favorites

import android.content.Context
import android.content.SharedPreferences
import com.example.newsapp.ui.main.accounts.UserSharedPreference

class FavSharedPreference(context: Context) {
    private val userSharedPreference = UserSharedPreference(context)
    private val sharedPreference: SharedPreferences = context.getSharedPreferences(
        userSharedPreference.getValue("username"),
        Context.MODE_PRIVATE
    )

    fun saveFav(key: String, value: Boolean) {
        val editor = sharedPreference.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun hasFav(key: String): Boolean {
        return sharedPreference.contains(key)
    }

    fun getKeys(): MutableSet<String> {
        return sharedPreference.all.keys
    }

    fun removeFav(key: String) {
        sharedPreference.edit()?.remove(key)?.apply()
    }
}