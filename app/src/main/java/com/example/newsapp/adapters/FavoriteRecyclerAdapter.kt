package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.NewsInfo

class FavoriteRecyclerAdapter(private val count:Int, private val dataArray:Array<NewsInfo>)
    : RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(cardView: View): RecyclerView.ViewHolder(cardView){
        var titleImage: ImageView =cardView.findViewById(R.id.recycler_title_img)
        var briefDesc: TextView =cardView.findViewById(R.id.recycler_brief_desc)
        var newsTime: TextView =cardView.findViewById(R.id.recycler_time)
        var favButton: ImageButton =cardView.findViewById(R.id.recycler_fav_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false))
    }


    override fun getItemCount(): Int {
        return count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleImage.setImageResource(dataArray[position].image)
        holder.briefDesc.text=dataArray[position].briefDesc
        holder.favButton.setImageResource(R.drawable.favorite)
        holder.newsTime.text=dataArray[position].time


    }
}