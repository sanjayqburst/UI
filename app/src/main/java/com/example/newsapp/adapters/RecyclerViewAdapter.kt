package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R

class RecyclerViewAdapter(private val count:Int, private val imgArray: Array<Int>,
                          private val briefArray: Array<String>, private val favImg:Array<Boolean>, private val newsTime:Array<String>)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

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
        holder.titleImage.setImageResource(imgArray[position])
        holder.briefDesc.text=briefArray[position]
        if (favImg[position]){
            holder.favButton.setImageResource(R.drawable.favorite)
        }else{
            holder.favButton.setImageResource(R.drawable.favorite_border)
        }

        holder.newsTime.text=newsTime[position]
    }

    override fun getItemCount(): Int {
        return count
    }
}