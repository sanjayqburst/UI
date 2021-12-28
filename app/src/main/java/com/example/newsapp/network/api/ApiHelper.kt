package com.example.newsapp.network.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getNewsData() = apiService.newsApiData()
}