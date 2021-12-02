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
import com.example.newsapp.R
import com.example.newsapp.model.NewsInfo
import com.example.newsapp.ui.DisplayNewsActivity
import com.example.newsapp.ui.favorites.FavSharedPreference

class NewsRecyclerAdapter(private val context: Context,private val count:Int, private val dataArray:MutableList<NewsInfo>)
    : RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {
    private val favSharedPreference=FavSharedPreference(context)

    inner class ViewHolder(cardView: View):RecyclerView.ViewHolder(cardView){
        var titleImage:ImageView=cardView.findViewById(R.id.recycler_title_img)
        var briefDesc:TextView=cardView.findViewById(R.id.recycler_brief_desc)
        var newsTime:TextView=cardView.findViewById(R.id.recycler_time)
        var favButton:ImageButton=cardView.findViewById(R.id.recycler_fav_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_recycler_card_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleImage.setImageResource(dataArray[position].image)
        holder.briefDesc.text=dataArray[position].briefDesc
        if (favSharedPreference.hasFav(dataArray[position].id)){
            holder.favButton.setImageResource(R.drawable.favorite)
        }else{
            holder.favButton.setImageResource(R.drawable.favorite_border)
        }
        holder.newsTime.text=dataArray[position].time

        holder.itemView.setOnClickListener {
            Log.d("Hello",favSharedPreference.getKeys().toString())
            val intent=Intent(context,DisplayNewsActivity::class.java)
            val bundle=Bundle()
            bundle.putParcelable("data",dataArray[position])
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
        holder.favButton.setOnClickListener {
            Log.d("Hello",dataArray[position].id.toString())
            if (favSharedPreference.hasFav(dataArray[position].id)){
                favSharedPreference.removeFav(dataArray[position].id)
                holder.favButton.setImageResource(R.drawable.favorite_border)

            }else{
                favSharedPreference.saveFav(dataArray[position].id,true)
                holder.favButton.setImageResource(R.drawable.favorite)

            }
        }
    }

    override fun getItemCount(): Int {
        return count
    }
}