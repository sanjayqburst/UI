package com.example.newsapp.network.api

import android.util.Log
import com.example.newsapp.model.NewsData
import com.example.newsapp.network.API_KEY

// Instance of API service
class ApiHelper(private val apiService: ApiService) {
    suspend fun getNewsData(): NewsData? {
        return try {
            val api = apiService.newsApiData(API_KEY)
            if (api.isSuccessful) {
                api.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("Error", "Api helper Error ${e.localizedMessage}")
            null
        }
    }
}
