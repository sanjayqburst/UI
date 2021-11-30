package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsapp.databinding.ActivityDisplayNewsBinding
import com.example.newsapp.model.NewsInfo

class DisplayNewsActivity : AppCompatActivity() {
    lateinit var displayNewsBinding: ActivityDisplayNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayNewsBinding= ActivityDisplayNewsBinding.inflate(layoutInflater)
        setContentView(displayNewsBinding.root)
        val brief=intent.getStringExtra("brief")
        val time=intent.getStringExtra("time")
        val img:Int?=intent.getStringExtra("image")?.toInt()
        displayNewsBinding.displayNewsBrief.text=brief
        displayNewsBinding.displayNewsTime.text=time
        if (img != null) {
            displayNewsBinding.displayTitleImage.setImageResource(img)
        }
    }
}