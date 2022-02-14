package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsFavourites::class], version = 1)
abstract class NewsFavouritesDatabase : RoomDatabase() {
    abstract val newsFavouritesDao: NewsFavouritesDao

    companion object {
        private const val DB_NAME = "news_favourites_db"

        @Volatile
        private var INSTANCE: NewsFavouritesDatabase? = null
        fun getInstance(context: Context): NewsFavouritesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, NewsFavouritesDatabase::class.java,
                        DB_NAME
                    ).build()
                }
                return instance
            }
        }
    }
}