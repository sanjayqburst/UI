package com.example.newsapp.ui.main.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.db.NewsFavourites
import com.example.newsapp.model.Article
import com.example.newsapp.ui.main.DisplayNewsActivity
import com.example.newsapp.ui.main.accounts.UserSharedPreference

// Adapter for News fragment recycler view

class NewsRecyclerAdapter(
    private val context: Context,
    private val dataArray: ArrayList<Article>
) : RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {


    //    Creating instance of sharedPreference
//    private val favSharedPreference = FavSharedPreference(context)
    private val userSharedPreference = UserSharedPreference(context)

    private var newsFavourites = ArrayList<NewsFavourites>()

    var onFavButtonChecked: ((Article, String) -> Unit)? = null

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
        if (newsFavourites.contains(
                NewsFavourites(
                    userSharedPreference.getValue("username"),
                    dataArray[position].url
                )
            )
        ) {
            holder.favButton.setImageResource(R.drawable.favorite)
        } else {
            holder.favButton.setImageResource(R.drawable.favorite_border)
        }
        holder.favButton.setOnClickListener {
            val user = userSharedPreference.getValue("username")
            val news = dataArray[position]
            news.let { onFavButtonChecked?.invoke(it, user) }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DisplayNewsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("NewsData", dataArray[position])
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
//        TODO: move with callback fun
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }


    fun updateFavourites(newsFavData: List<NewsFavourites>) {
        Log.d("Error", "Function called")
        newsFavourites.apply {
            clear()
            addAll(newsFavData)
            notifyDataSetChanged()
        }
    }

    fun addNews(newsData: List<Article>) {
        this.dataArray.apply {
            clear()
            addAll(newsData)
            notifyDataSetChanged()
        }
    }
}