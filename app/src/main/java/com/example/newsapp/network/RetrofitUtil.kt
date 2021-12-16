package com.example.newsapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitUtil {
    private val retrofit = Retrofit.Builder().baseUrl(
        BASE_URL
    ).addConverterFactory(GsonConverterFactory.create()).build()
    private val callHeadlines = retrofit.create(ApiInterface::class.java)
    val headlines = callHeadlines.topHeadlines()

}