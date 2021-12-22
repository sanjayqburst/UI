package com.example.newsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Parcelable data class for api data
@Parcelize
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source,
    val title: String?,
    val url: String,
    val urlToImage: String?
) : Parcelable