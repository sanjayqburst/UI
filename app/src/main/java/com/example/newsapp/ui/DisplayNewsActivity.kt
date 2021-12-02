package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityDisplayNewsBinding
import com.example.newsapp.model.NewsInfo
import com.example.newsapp.ui.favorites.FavSharedPreference

class DisplayNewsActivity : AppCompatActivity() {
    private lateinit var displayNewsBinding: ActivityDisplayNewsBinding
    private lateinit var favSharedPreference: FavSharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayNewsBinding= ActivityDisplayNewsBinding.inflate(layoutInflater)
        favSharedPreference=FavSharedPreference(this)

        setContentView(displayNewsBinding.root)
        val bundle=intent.extras
        val data: NewsInfo? =bundle?.getParcelable("data")
        displayNewsBinding.displayNewsBrief.text= data?.content
        displayNewsBinding.displayNewsTime.text= data?.time
        if (data != null) {
            displayNewsBinding.displayTitleImage.setImageResource(data.image)
            if (favSharedPreference.hasFav(data.id)){
                displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite)
            }else{
                displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite_border)
            }
        }
        displayNewsBinding.displayFavBtn.setOnClickListener {
            if (data != null) {
                if (favSharedPreference.hasFav(data.id)){
                    favSharedPreference.removeFav(data.id)
                    displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite_border)

                }else{
                    favSharedPreference.saveFav(data.id,true)
                    displayNewsBinding.displayFavBtn.setImageResource(R.drawable.favorite)

                }
            }
        }
        displayNewsBinding.displayBackButton.setOnClickListener {
            finish()
        }



    }
}