package com.example.newsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsData(
    val articles: List<Article>,
    val status: String?,
    val totalResults: Int?
) : Parcelable