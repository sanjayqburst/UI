package com.example.newsapp.adapters

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
import com.example.newsapp.model.Article
import com.example.newsapp.ui.DisplayNewsActivity
import com.example.newsapp.ui.favorites.FavSharedPreference

class NewsRecyclerAdapter(
    private val context: Context,
    private val dataArray: List<Article>
) : RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {

    private val favSharedPreference = FavSharedPreference(context)

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


//        if (dataArray[position].description.length > 150) {
//            val str = "${
//                dataArray[position].description.subSequence(
//                    0,
//                    149
//                )
//            }... \n \n  " + context.getString(R.string.read_more)
//            holder.briefDesc.text = str
//
//        } else {
//            val str =
//                "${dataArray[position].description}... \n \n  " + context.getString(R.string.read_more)
        Log.d("Msg", "${dataArray[position].title}")
        holder.briefDesc.text = dataArray[position].title
//        }
        Glide.with(context).load(dataArray[position].urlToImage).centerCrop()
            .into(holder.titleImage)

        holder.newsTime.text = dataArray[position].publishedAt

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

    override fun getItemCount(): Int {
        return dataArray.size
    }
}