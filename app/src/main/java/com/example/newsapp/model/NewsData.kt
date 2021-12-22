package com.example.newsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Parcelable data class for api data
@Parcelize
data class NewsData(
    val articles: List<Article>,
    val status: String?,
    val totalResults: Int?
) : Parcelable