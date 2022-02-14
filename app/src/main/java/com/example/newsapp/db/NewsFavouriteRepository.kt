package com.example.newsapp.db

import android.content.Context
import android.util.Log
import com.example.newsapp.ui.main.viewmodel.NewsViewModel


class NewsFavouriteRepository(private val newsFavouritesDao: NewsFavouritesDao) {

    suspend fun insertFavourites(newsFavourite: NewsFavourites) =
        newsFavouritesDao.insertFavourites(newsFavourite)

    suspend fun deleteFavourites(newsFavourite: NewsFavourites) =
        newsFavouritesDao.deleteFavourites(newsFavourite)

    fun getFavourites(uId: String) = newsFavouritesDao.getFavourites(uId)

    suspend fun delete() = newsFavouritesDao.delete()

    suspend fun checkItemExists(link: String, uId: String): NewsFavourites {
        Log.d("Error", "${newsFavouritesDao.checkItemExists(link, uId)}")
        return newsFavouritesDao.checkItemExists(link, uId)
    }


}