package com.example.newsapp.network.api

import com.example.newsapp.network.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton retrofit instance
object RetrofitBuilder {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}