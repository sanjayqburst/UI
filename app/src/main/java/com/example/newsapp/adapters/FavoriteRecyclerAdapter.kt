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
import com.example.newsapp.R
import com.example.newsapp.model.NewsInfo
import com.example.newsapp.ui.DisplayNewsActivity

class FavoriteRecyclerAdapter(val context: Context,private val count:Int, private val dataArray:Array<NewsInfo>)
    : RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(cardView: View): RecyclerView.ViewHolder(cardView){
        var titleImage: ImageView =cardView.findViewById(R.id.recycler_title_img)
        var briefDesc: TextView =cardView.findViewById(R.id.recycler_brief_desc)
        var newsTime: TextView =cardView.findViewById(R.id.recycler_time)
        var favButton: ImageButton =cardView.findViewById(R.id.recycler_fav_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_recycler_card_layout,parent,false))
    }


    override fun getItemCount(): Int {
        return count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleImage.setImageResource(dataArray[position].image)
        holder.briefDesc.text=dataArray[position].briefDesc
        holder.favButton.setImageResource(R.drawable.favorite)
        holder.newsTime.text=dataArray[position].time

        holder.itemView.setOnClickListener {
            val intent= Intent(context, DisplayNewsActivity::class.java)
            val bundle= Bundle()
            bundle.putParcelable("data",dataArray[position])
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}