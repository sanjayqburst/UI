package com.example.newsapp.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.ui.main.accounts.UserSharedPreference
import kotlinx.coroutines.launch

class NewsFavouriteViewModel(
    private val user: String,
    private val newsFavouriteRepository: NewsFavouriteRepository
) : ViewModel() {
    private var favouritesLiveData: LiveData<List<NewsFavourites>> = newsFavouriteRepository.getFavourites(user)


    fun getFavorites(): LiveData<List<NewsFavourites>> {
        return newsFavouriteRepository.getFavourites(user)
    }

    fun addToFavourites(newsFavourite: NewsFavourites) = viewModelScope.launch {
        newsFavouriteRepository.insertFavourites(newsFavourite)
    }

    fun deleteFromFavourites(newsFavourite: NewsFavourites) = viewModelScope.launch {
        newsFavouriteRepository.deleteFavourites(newsFavourite)
    }

    fun checkItemExists(link: String, uId: String): MutableLiveData<NewsFavourites?> {
        val newsFavouriteLiveData = MutableLiveData<NewsFavourites?>()
        viewModelScope.launch {
            val newsData = newsFavouriteRepository.checkItemExists(link, uId)
            newsFavouriteLiveData.postValue(newsData)
        }
        return newsFavouriteLiveData
    }

    fun deleteAll() {
        viewModelScope.launch {
            newsFavouriteRepository.delete()
        }
    }


}