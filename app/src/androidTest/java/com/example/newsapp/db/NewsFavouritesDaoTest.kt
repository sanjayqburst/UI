package com.example.newsapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.newsapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NewsFavouritesDaoTest {

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    private lateinit var database: NewsFavouritesDatabase
    private lateinit var dao: NewsFavouritesDao

    @Before
    fun setup(){
        database= Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),NewsFavouritesDatabase::class.java).allowMainThreadQueries().build()
        dao=database.newsFavouritesDao
    }

    @Test
    fun insertFavouriteItem()= runBlockingTest {
        val newsFavourites=NewsFavourites("sample","www.google.com")
        dao.insertFavourites(newsFavourites)
        val allFav=dao.getFavourites("sample").getOrAwaitValue()

        assertThat(allFav).contains(newsFavourites)

    }

    @Test
    fun deleteFavItem()= runBlockingTest {
        val newsFavourites=NewsFavourites("sam","www.google.com")
        dao.insertFavourites(newsFavourites)
        dao.delete()
        val allFav=dao.getFavourites("sam").getOrAwaitValue()

        assertThat(allFav).doesNotContain(newsFavourites)
    }


    @After
    fun tearDown(){
        database.close()
    }
}