package com.example.newsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.network.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    companion object {
        private var userData = MutableLiveData<NewsData>()

        init {
            userData.value = null
        }

        fun getApiData(getResponse: (NewsData?) -> Unit) {
            val response = RetrofitUtil.headlines
            response.clone().enqueue(object : Callback<NewsData> {
                override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                    userData.value = response.body()
                    getResponse(response.body())
                }

                override fun onFailure(call: Call<NewsData>, t: Throwable) {
                    userData.value = null
                    getResponse(null)
                }
            })
        }
    }

}