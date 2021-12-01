package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityDisplayNewsBinding
import com.example.newsapp.model.NewsInfo

class DisplayNewsActivity : AppCompatActivity() {
    private lateinit var displayNewsBinding: ActivityDisplayNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayNewsBinding= ActivityDisplayNewsBinding.inflate(layoutInflater)
        title=""
        actionBar?.setIcon(R.drawable.favorite)

        setContentView(displayNewsBinding.root)
        val bundle=intent.extras
        val data: NewsInfo? =bundle?.getParcelable("data")
        displayNewsBinding.displayNewsBrief.text= data?.content
        displayNewsBinding.displayNewsTime.text= data?.time
        if (data != null) {
            displayNewsBinding.displayTitleImage.setImageResource(data.image)
            if (data.fav){
                displayNewsBinding.displayImageBtn.setImageResource(R.drawable.favorite)
            }else{
                displayNewsBinding.displayImageBtn.setImageResource(R.drawable.favorite_border)
            }
        }
        displayNewsBinding.displayBackButton.setOnClickListener {
            finish()
        }



    }
}