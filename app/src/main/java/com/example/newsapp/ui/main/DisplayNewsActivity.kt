package com.example.newsapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityDisplayNewsBinding
import com.example.newsapp.db.*
import com.example.newsapp.model.Article
import com.example.newsapp.network.api.ApiHelper
import com.example.newsapp.network.api.RetrofitBuilder
import com.example.newsapp.ui.base.ViewModelFactory
import com.example.newsapp.ui.main.accounts.UserSharedPreference
import com.example.newsapp.ui.main.viewmodel.NewsViewModel

class DisplayNewsActivity : AppCompatActivity() {
    private lateinit var displayNewsBinding: ActivityDisplayNewsBinding
    private lateinit var newsFavouriteViewModel: NewsFavouriteViewModel
    private lateinit var userSharedPreference: UserSharedPreference
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = NewsFavouritesDatabase.getInstance(this).newsFavouritesDao
        newsViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), dao)
            )[NewsViewModel::class.java]
        val repository = NewsFavouriteRepository(dao)
        val factory = NewsFavouritesViewModelFactory(this, repository)
        newsFavouriteViewModel =
            ViewModelProvider(this, factory)[NewsFavouriteViewModel::class.java]

        displayNewsBinding = ActivityDisplayNewsBinding.inflate(layoutInflater)
        userSharedPreference = UserSharedPreference(this)
        val user = userSharedPreference.getValue("username")

        setContentView(displayNewsBinding.root)
        val bundle = intent.extras
        val data: Article? = bundle?.getParcelable("NewsData")
        if (data?.content != null) {
            displayNewsBinding.displayNewsBrief.text = data.content
        } else {
            displayNewsBinding.displayNewsBrief.text = data?.description
        }
        displayNewsBinding.displayNewsTime.text = data?.publishedAt
        if (data != null) {
            Glide.with(applicationContext).load(data.urlToImage).centerCrop()
                .into(displayNewsBinding.displayTitleImage)
            newsFavouriteViewModel.getFavorites().observe(this) { it ->
                Log.d("Error", "$it")
                it.let {
                    if (it.contains(NewsFavourites(user, data.url))) {
                        displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite)
                    } else {
                        displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite_border)
                    }
                }
            }

        }

        displayNewsBinding.displayFavBtn.setOnClickListener {
            if (data != null) {
                val news = NewsFavourites(user, data.url)
                newsFavouriteViewModel.checkItemExists(data.url, user).observe(this) {
                    if (it == null) {
                        newsFavouriteViewModel.addToFavourites(news)
                        displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite_border)
                    } else {
                        newsFavouriteViewModel.deleteFromFavourites(it)
                        displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite)
                    }
                }
            }
        }

        displayNewsBinding.displayBackButton.setOnClickListener {
            finish()
        }
    }

}