package com.example.newsapp.network.api

import com.example.newsapp.model.NewsData

// Instance of API service
class ApiHelper(private val apiService: ApiService) {
    suspend fun getNewsData(): NewsData {
        return apiService.newsApiData()
    }
}