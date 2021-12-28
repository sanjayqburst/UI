package com.example.newsapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityDisplayNewsBinding
import com.example.newsapp.model.Article
import com.example.newsapp.ui.main.favorites.FavSharedPreference

class DisplayNewsActivity : AppCompatActivity() {
    private lateinit var displayNewsBinding: ActivityDisplayNewsBinding
    private lateinit var favSharedPreference: FavSharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayNewsBinding = ActivityDisplayNewsBinding.inflate(layoutInflater)
        favSharedPreference = FavSharedPreference(this)
        setContentView(displayNewsBinding.root)
        val bundle = intent.extras
        val data: Article? = bundle?.getParcelable("NewsData")
        displayNewsBinding.displayNewsBrief.text = data?.description
        displayNewsBinding.displayNewsTime.text = data?.publishedAt
        if (data != null) {
            Glide.with(applicationContext).load(data.urlToImage).centerCrop()
                .into(displayNewsBinding.displayTitleImage)
            if (favSharedPreference.hasFav(data.url)) {
                displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite)
            } else {
                displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite_border)
            }
        }

        displayNewsBinding.displayFavBtn.setOnClickListener {
            if (data != null) {
                if (favSharedPreference.hasFav(data.url)) {
                    favSharedPreference.removeFav(data.url)
                    displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite_border)
                } else {
                    favSharedPreference.saveFav(data.url, true)
                    displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite)
                }
            }
        }

        displayNewsBinding.displayBackButton.setOnClickListener {
            finish()
        }
    }
}