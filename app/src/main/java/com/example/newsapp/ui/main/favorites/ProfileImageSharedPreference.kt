package com.example.newsapp.ui.main.favorites

import android.content.Context
import android.content.SharedPreferences
import com.example.newsapp.ui.main.accounts.UserSharedPreference

class ProfileImageSharedPreference(context: Context) {
    private val userSharedPreference = UserSharedPreference(context)
    private val sharedPreference: SharedPreferences = context.getSharedPreferences(
        userSharedPreference.getValue("username"),
        Context.MODE_PRIVATE
    )
    private val editor = sharedPreference.edit()

    fun saveImageUri(key: String, value: String) {
        editor?.putString(key, value)
        editor?.apply()
    }

    fun getValue(): String {
        return sharedPreference.getString("ProfileImage", "ProfileImage").toString()
    }

    fun hasFav(key: String): Boolean {
        return sharedPreference.contains(key)
    }


    fun removeFav(key: String) {
        sharedPreference.edit()?.remove(key)?.apply()
    }
}