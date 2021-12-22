package com.example.newsapp.network

import com.example.newsapp.model.NewsData
import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {
    @GET("top-headlines?country=us&apiKey=$API_KEY")
    fun topHeadlines(): Call<NewsData>
}
