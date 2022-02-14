package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsFavouritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourites(newsFavourites: NewsFavourites): Long

    @Query("SELECT * FROM news_favourites WHERE favourites_user_id LIKE :uId")
    fun getFavourites(uId: String): LiveData<List<NewsFavourites>>

    @Query("SELECT * FROM news_favourites WHERE favourites_user_id LIKE :uId")
    fun getFavList(uId: String):List<NewsFavourites>

    @Delete
    suspend fun deleteFavourites(newsFavourites: NewsFavourites): Int

    @Query("DELETE FROM news_favourites ")
    suspend fun delete()

    @Query("SELECT * FROM news_favourites WHERE favourites_links like:link AND favourites_user_id LIKE :uId")
    suspend fun checkItemExists(link: String, uId: String): NewsFavourites
}