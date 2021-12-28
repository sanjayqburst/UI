package com.example.newsapp.ui.main.adapters

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
import com.example.newsapp.ui.main.DisplayNewsActivity
import com.example.newsapp.ui.main.favorites.FavSharedPreference

// Adapter for favourites recycler view
class FavoriteRecyclerAdapter(val context: Context, private var dataArray: ArrayList<Article>) :
    RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder>() {
    private var favSharedPreference = FavSharedPreference(context)

    // View holder for adapter
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


    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Image loading with glide library
        Glide.with(context).load(dataArray[position].urlToImage).into(holder.titleImage)

//        Assigning corresponding values to view holder
        val str =
            "${dataArray[position].description}... \n \n  " + context.getString(R.string.read_more)
        holder.briefDesc.text = str
        holder.favButton.setImageResource(R.drawable.favorite)
        holder.newsTime.text = dataArray[position].publishedAt

//        On news card click event
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DisplayNewsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("NewsData", dataArray[position])
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
//        On favourite button click event
        holder.favButton.setOnClickListener {
            favButtonListener(dataArray, holder, position)
        }
    }

    //    Fun for on fav button click event
    private fun favButtonListener(
        dataArray: MutableList<Article>,
        holder: ViewHolder,
        position: Int
    ) {
        if (favSharedPreference.hasFav(dataArray[position].url)) {
            favSharedPreference.removeFav(dataArray[position].url)
            holder.favButton.setImageResource(R.drawable.favorite_border)
            dataArray.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, dataArray.size)
        } else {
            favSharedPreference.saveFav(dataArray[position].url, true)
            holder.favButton.setImageResource(R.drawable.favorite)
        }
    }

    fun addNews(newsData: List<Article>) {
        this.dataArray.apply {
            clear()
            addAll(newsData)
        }
    }
}