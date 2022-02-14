package com.example.newsapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_favourites")
data class NewsFavourites(
    @ColumnInfo(name = "favourites_user_id")
    var userId: String,
    @ColumnInfo(name = "favourites_links")
    var favourLink: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "favourites_id")
    var id: Int = 0
}
