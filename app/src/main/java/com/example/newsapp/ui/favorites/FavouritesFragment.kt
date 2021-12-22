package com.example.newsapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapters.FavoriteRecyclerAdapter
import com.example.newsapp.databinding.FragmentFavouritesBinding
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsViewModel


class FavouritesFragment : Fragment() {
    private lateinit var favouritesBinding: FragmentFavouritesBinding
    private lateinit var newLayoutManager: LinearLayoutManager
    private lateinit var favSharedPreference: FavSharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
        val favSharedPreference = FavSharedPreference(requireContext())
        val idArray = favSharedPreference.getKeys()
        NewsViewModel.getApiData { it ->
            if (it != null) {
                val newArr: List<Article> = it.articles.filter { it.url in idArray }
                val favoriteRecyclerAdapter =
                    FavoriteRecyclerAdapter(requireContext(), newArr.toMutableList())
                favouritesBinding.favoriteCardRecycler.adapter = favoriteRecyclerAdapter
                newLayoutManager = LinearLayoutManager(requireContext())
                favouritesBinding.favoriteCardRecycler.layoutManager = newLayoutManager
            }
        }

    }

}