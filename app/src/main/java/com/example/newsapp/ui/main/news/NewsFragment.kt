package com.example.newsapp.ui.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.Article
import com.example.newsapp.network.api.ApiHelper
import com.example.newsapp.network.api.RetrofitBuilder
import com.example.newsapp.ui.base.ViewModelFactory
import com.example.newsapp.ui.main.adapters.NewsRecyclerAdapter
import com.example.newsapp.ui.main.viewmodel.NewsViewModel
import com.example.newsapp.utils.Status.*
import com.example.newsapp.utils.getDateOrDay
import java.util.*


class NewsFragment : Fragment() {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsRecyclerAdapter: NewsRecyclerAdapter
    private lateinit var newsBinding: FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsBinding = FragmentNewsBinding.inflate(layoutInflater)
        setupViewModel()
        setupUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return newsBinding.root
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
        newsBinding.apply {
            newsDate.text = Date().getDateOrDay("dd MMM yyyy")
            newsDay.text = Date().getDateOrDay("EEEE")
        }
    }


    private fun setupViewModel() {
        newsViewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
                NewsViewModel::class.java
            )
    }

    private fun setupObservers() {
        newsViewModel.getNewsData().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        newsBinding.newsCardRecycler.visibility = View.VISIBLE
                        newsBinding.progressBar.visibility = View.GONE
                        resource.data?.let { newsData -> retrieveData(newsData = newsData.articles) }

                    }
                    ERROR -> {
                        newsBinding.apply {
                            newsCardRecycler.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                    }
                    LOADING -> {
                        newsBinding.apply {
                            newsCardRecycler.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                        }
                    }

                }
            }
        })
    }

    private fun setupUI() {
        newsBinding.apply {
            newsCardRecycler.layoutManager = LinearLayoutManager(requireContext())
            newsRecyclerAdapter = NewsRecyclerAdapter(requireContext(), arrayListOf())
            newsCardRecycler.addItemDecoration(
                DividerItemDecoration(
                    newsCardRecycler.context,
                    (newsCardRecycler.layoutManager as LinearLayoutManager).orientation
                )
            )
            newsCardRecycler.adapter = newsRecyclerAdapter
        }
    }


    private fun retrieveData(newsData: List<Article>) {
        newsRecyclerAdapter.apply {
            addNews(newsData)
        }
    }


}