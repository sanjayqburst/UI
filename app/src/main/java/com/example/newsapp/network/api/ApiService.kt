package com.example.newsapp.network.api

import com.example.newsapp.model.NewsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //    GET request for API call
    @GET("top-headlines?country=us")
    suspend fun newsApiData(@Query("apiKey") apiKey: String): Response<NewsData>
}