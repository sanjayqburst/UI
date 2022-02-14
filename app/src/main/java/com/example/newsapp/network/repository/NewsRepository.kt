package com.example.newsapp.network.repository

import androidx.lifecycle.liveData
import com.example.newsapp.db.NewsFavouritesDao
import com.example.newsapp.model.Article
import com.example.newsapp.network.api.ApiHelper
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers

// Instance of API helper

class NewsRepository(
    private val apiHelper: ApiHelper,
    private val newsFavouritesDao: NewsFavouritesDao
) {

    fun getNewsData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getNewsData()))
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = "Error ${ex.localizedMessage}"))
        }
    }

    fun getFavData(uid: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val articleArray = arrayListOf<Article>()
            val data = apiHelper.getNewsData()
            val favData = newsFavouritesDao.getFavList(uid)
            val array = arrayListOf<String>()
            for (item in favData) {
                array.add(item.favourLink)
            }
            if (data != null) {
                for (item in data.articles) {
                    if (item.url in array) {
                        articleArray.add(item)
                    }
                }
            }
            emit(Resource.success(data = articleArray))

        } catch (e: Exception) {
            emit(Resource.error(data = null, message = "Error ${e.localizedMessage}"))
        }
    }

}