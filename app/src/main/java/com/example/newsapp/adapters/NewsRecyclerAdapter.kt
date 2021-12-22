package com.example.newsapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.ui.DisplayNewsActivity
import com.example.newsapp.ui.favorites.FavSharedPreference

// Adapter for News fragment recycler view

class NewsRecyclerAdapter(
    private val context: Context,
    private val dataArray: List<Article>
) : RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {

    //    Creating instance of sharedPreference
    private val favSharedPreference = FavSharedPreference(context)

    //    Creating view holder for adapter
    inner class ViewHolder(cardView: View) : RecyclerView.ViewHolder(cardView) {
        var titleImage: ImageView = cardView.findViewById(R.id.recycler_title_img)
        var briefDesc: TextView = cardView.findViewById(R.id.recycler_brief_desc)
        var newsTime: TextView = cardView.findViewById(R.id.recycler_time)
        var favButton: ImageButton = cardView.findViewById(R.id.recycler_fav_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.news_recycler_card_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Loading image with glide library
        Glide.with(context).load(dataArray[position].urlToImage).centerCrop()
            .into(holder.titleImage)
//        Assigning values to view holder
        holder.briefDesc.text = dataArray[position].title
        holder.newsTime.text = dataArray[position].publishedAt

//        Setting fav button to selected or unselected
        if (favSharedPreference.hasFav(dataArray[position].url)) {
            holder.favButton.setImageResource(R.drawable.favorite)
        } else {
            holder.favButton.setImageResource(R.drawable.favorite_border)
        }
        holder.favButton.setOnClickListener {
            favButtonListener(dataArray.toMutableList(), holder, position)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DisplayNewsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("NewsData", dataArray[position])
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    //    Fun for fav button click event
    private fun favButtonListener(
        dataArray: MutableList<Article>,
        holder: ViewHolder,
        position: Int
    ) {
        if (favSharedPreference.hasFav(dataArray[position].url)) {
            favSharedPreference.removeFav(dataArray[position].url)
            holder.favButton.setImageResource(R.drawable.favorite_border)
        } else {
            favSharedPreference.saveFav(dataArray[position].url, true)
            holder.favButton.setImageResource(R.drawable.favorite)
        }
    }
}