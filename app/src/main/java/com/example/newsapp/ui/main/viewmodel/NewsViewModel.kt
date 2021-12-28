package com.example.newsapp.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.newsapp.network.repository.NewsRepository
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getNewsData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = newsRepository.getNewsData()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occurred"))
        }
    }
}