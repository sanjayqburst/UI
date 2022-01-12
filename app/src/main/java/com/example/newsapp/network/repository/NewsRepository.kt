package com.example.newsapp.network.repository

import com.example.newsapp.model.NewsData
import com.example.newsapp.network.api.ApiHelper

// Instance of API helper
class NewsRepository(private val apiHelper: ApiHelper) {
    suspend fun getNewsData(): NewsData {
        return apiHelper.getNewsData()
    }
}