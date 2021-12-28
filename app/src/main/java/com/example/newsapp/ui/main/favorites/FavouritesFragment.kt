package com.example.newsapp.ui.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentFavouritesBinding
import com.example.newsapp.model.Article
import com.example.newsapp.network.api.ApiHelper
import com.example.newsapp.network.api.RetrofitBuilder
import com.example.newsapp.ui.base.ViewModelFactory
import com.example.newsapp.ui.main.adapters.FavoriteRecyclerAdapter
import com.example.newsapp.ui.main.viewmodel.NewsViewModel
import com.example.newsapp.utils.Status


class FavouritesFragment : Fragment() {
    private lateinit var favouritesBinding: FragmentFavouritesBinding
    private lateinit var favSharedPreference: FavSharedPreference
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var favoriteRecyclerAdapter: FavoriteRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favouritesBinding = FragmentFavouritesBinding.inflate(layoutInflater)
        setupViewModel()
        setupUI()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favSharedPreference = FavSharedPreference(requireContext())
        return favouritesBinding.root
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
    }

    private fun setupViewModel() {
        newsViewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
                NewsViewModel::class.java
            )
    }

    private fun setupObservers() {
        newsViewModel.getNewsData().observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val favSharedPreference = FavSharedPreference(requireContext())
                        val idArray = favSharedPreference.getKeys()
                        favouritesBinding.favoriteCardRecycler.visibility = View.VISIBLE
                        favouritesBinding.progressBarFav.visibility = View.GONE
                        resource.data?.let { newsData -> retrieveData(newsData = newsData.articles.filter { it.url in idArray }) }

                    }
                    Status.ERROR -> {
                        favouritesBinding.apply {
                            favoriteCardRecycler.visibility = View.VISIBLE
                            progressBarFav.visibility = View.GONE
                        }
                    }
                    Status.LOADING -> {
                        favouritesBinding.apply {
                            favoriteCardRecycler.visibility = View.GONE
                            progressBarFav.visibility = View.VISIBLE
                        }
                    }

                }
            }
        })
    }

    private fun setupUI() {
        favouritesBinding.apply {
            favoriteCardRecycler.layoutManager = LinearLayoutManager(requireContext())
            favoriteRecyclerAdapter = FavoriteRecyclerAdapter(requireContext(), arrayListOf())
            favoriteCardRecycler.addItemDecoration(
                DividerItemDecoration(
                    favoriteCardRecycler.context,
                    (favoriteCardRecycler.layoutManager as LinearLayoutManager).orientation
                )
            )
            favoriteCardRecycler.adapter = favoriteRecyclerAdapter
        }
    }


    private fun retrieveData(newsData: List<Article>) {
        favoriteRecyclerAdapter.apply {
            addNews(newsData)
            notifyDataSetChanged()
        }
    }

}