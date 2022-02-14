package com.example.newsapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.db.NewsFavouritesDao
import com.example.newsapp.network.api.ApiHelper
import com.example.newsapp.network.repository.NewsRepository
import com.example.newsapp.ui.main.viewmodel.NewsViewModel

class ViewModelFactory(
    private val apiHelper: ApiHelper,
    private val newsFavouritesDao: NewsFavouritesDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(NewsRepository(apiHelper, newsFavouritesDao)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
