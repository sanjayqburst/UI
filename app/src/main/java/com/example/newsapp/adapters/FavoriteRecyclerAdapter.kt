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

class FavoriteRecyclerAdapter(val context: Context, private var dataArray:MutableList<NewsInfo>)
    : RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder>() {
    private var favSharedPreference=FavSharedPreference(context)

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
        return dataArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleImage.setImageResource(dataArray[position].image)
        val str="${dataArray[position].briefDesc.subSequence(0,149)}... \n \n  "+ context.getString(R.string.read_more)
        holder.briefDesc.text=str
        holder.favButton.setImageResource(R.drawable.favorite)
        holder.newsTime.text=dataArray[position].time

        holder.itemView.setOnClickListener {
            val intent= Intent(context, DisplayNewsActivity::class.java)
            val bundle= Bundle()
            bundle.putParcelable("data",dataArray[position])
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
        holder.favButton.setOnClickListener {
            favButtonListener(dataArray,holder,position)
        }
    }

    private fun favButtonListener(dataArray: MutableList<NewsInfo>,holder: ViewHolder,position: Int){
        if (favSharedPreference.hasFav(dataArray[position].id)){

            favSharedPreference.removeFav(dataArray[position].id)
            holder.favButton.setImageResource(R.drawable.favorite_border)

            dataArray.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,dataArray.size)

        }else{

            favSharedPreference.saveFav(dataArray[position].id,true)
            holder.favButton.setImageResource(R.drawable.favorite)
        }
    }
}