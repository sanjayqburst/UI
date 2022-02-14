package com.example.newsapp.db

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class NewsFavouritesViewModelFactory(
    val user: String,
    private val newsFavouriteRepository: NewsFavouriteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsFavouriteViewModel::class.java)) {
            return NewsFavouriteViewModel(user, newsFavouriteRepository) as T
        }
        throw IllegalArgumentException("Unknown View model class")
    }
}