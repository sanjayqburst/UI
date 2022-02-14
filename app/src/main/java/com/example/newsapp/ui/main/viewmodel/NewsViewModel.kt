package com.example.newsapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsData
import com.example.newsapp.network.repository.NewsRepository
import com.example.newsapp.utils.Resource


class NewsViewModel(private val newsRepository: NewsRepository):ViewModel(){
    fun getNewsData(): LiveData<Resource<NewsData?>> {
        return newsRepository.getNewsData()
    }
    fun getFavData(uId:String): LiveData<Resource<ArrayList<Article>>> {
        return newsRepository.getFavData(uId)
    }

}