package com.example.newsapp.network.api

import com.example.newsapp.model.NewsData

class ApiHelper(private val apiService: ApiService) {
    suspend fun getNewsData(): NewsData {
        return apiService.newsApiData()
    }
}