package com.example.newsapp.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapters.FavoriteRecyclerAdapter
import com.example.newsapp.databinding.FragmentFavouritesBinding
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsData
import com.example.newsapp.network.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavouritesFragment : Fragment() {
    private lateinit var favouritesBinding: FragmentFavouritesBinding
    private lateinit var newLayoutManager: LinearLayoutManager
    private lateinit var favSharedPreference: FavSharedPreference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favSharedPreference = FavSharedPreference(requireContext())
        favouritesBinding = FragmentFavouritesBinding.inflate(layoutInflater)


        return favouritesBinding.root


    }

    override fun onResume() {
        super.onResume()
        val api = RetrofitUtil.headlines
        val favSharedPreference = FavSharedPreference(requireContext())
        val idArray = favSharedPreference.getKeys()
        Log.d("Msg", "create view $idArray")
        api.clone().enqueue(object : Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {

                val newArr: List<Article> = response.body()!!.articles.filter { it.url in idArray }
                val favoriteRecyclerAdapter =
                    FavoriteRecyclerAdapter(requireContext(), newArr.toMutableList())
                favouritesBinding.favoriteCardRecycler.adapter = favoriteRecyclerAdapter
                newLayoutManager = LinearLayoutManager(requireContext())
                favouritesBinding.favoriteCardRecycler.layoutManager = newLayoutManager
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                Log.d("Msg", "Failed", t)
            }
        })
    }


}