package com.example.newsapp.network.api

import com.example.newsapp.model.NewsData
import com.example.newsapp.network.API_KEY
import retrofit2.http.GET

interface ApiService {
    @GET("top-headlines?country=us&apiKey=$API_KEY")
    suspend fun newsApiData(): NewsData
}