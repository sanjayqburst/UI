package com.example.newsapp.ui.main.adapters

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
import com.example.newsapp.ui.main.accounts.UserSharedPreference

// Adapter for News fragment recycler view
class NewsRecyclerAdapter(
    private val dataArray: ArrayList<Article>
) : RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {


    //    Creating instance of sharedPreference
    private lateinit var userSharedPreference: UserSharedPreference
    private var newsFavourites = ArrayList<NewsFavourites>()

    var onFavButtonChecked: ((Article) -> Unit)? = null
    var onCardClick: ((Article) -> Unit)? = null

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
        userSharedPreference = UserSharedPreference(holder.itemView.context)
        Glide.with(holder.itemView.context).load(dataArray[position].urlToImage).centerCrop()
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
            val news = dataArray[position]
            news.let { onFavButtonChecked?.invoke(it) }
        }

        holder.itemView.setOnClickListener {
            it.let { onCardClick?.invoke(dataArray[position]) }
        }
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