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

class NewsRecyclerAdapter(private val context: Context,private val count:Int, private val dataArray:Array<NewsInfo>)
    : RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(cardView: View):RecyclerView.ViewHolder(cardView){
        var titleImage:ImageView=cardView.findViewById(R.id.recycler_title_img)
        var briefDesc:TextView=cardView.findViewById(R.id.recycler_brief_desc)
        var newsTime:TextView=cardView.findViewById(R.id.recycler_time)
        var favButton:ImageButton=cardView.findViewById(R.id.recycler_fav_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleImage.setImageResource(dataArray[position].image)
        holder.briefDesc.text=dataArray[position].briefDesc
        if (dataArray[position].fav){
            holder.favButton.setImageResource(R.drawable.favorite)
        }else{
            holder.favButton.setImageResource(R.drawable.favorite_border)
        }
        holder.newsTime.text=dataArray[position].time

        holder.itemView.setOnClickListener {
            val intent=Intent(context,DisplayNewsActivity::class.java)
            intent.putExtra("image",dataArray[position].image.toString())
            intent.putExtra("brief",dataArray[position].briefDesc)
            intent.putExtra("time",dataArray[position].time)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return count
    }
}