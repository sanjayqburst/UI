package com.example.newsapp.ui.main.news

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.db.*
import com.example.newsapp.model.Article
import com.example.newsapp.network.ConnectivityStatus
import com.example.newsapp.network.api.ApiHelper
import com.example.newsapp.network.api.RetrofitBuilder
import com.example.newsapp.ui.base.ViewModelFactory
import com.example.newsapp.ui.main.DisplayNewsActivity
import com.example.newsapp.ui.main.accounts.UserSharedPreference
import com.example.newsapp.ui.main.adapters.NewsRecyclerAdapter
import com.example.newsapp.ui.main.viewmodel.NewsViewModel
import com.example.newsapp.utils.Status.*
import com.example.newsapp.utils.getDateOrDay
import com.google.android.material.snackbar.Snackbar
import java.util.*


class NewsFragment : Fragment() {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsRecyclerAdapter: NewsRecyclerAdapter
    private lateinit var newsFavouriteViewModel: NewsFavouriteViewModel
    private lateinit var userSharedPreferences: UserSharedPreference
    private lateinit var newsBinding: FragmentNewsBinding
    private lateinit var user:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsBinding = FragmentNewsBinding.inflate(layoutInflater)
        setupViewModel()
        userSharedPreferences= UserSharedPreference(requireContext())
        user=userSharedPreferences.getValue("username")
        newsRecyclerAdapter = NewsRecyclerAdapter(arrayListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        newsBinding.swipeRefresh.setOnRefreshListener {
            setupNetwork()
            newsBinding.swipeRefresh.isRefreshing = false
        }
        return newsBinding.root
    }

    override fun onStart() {
        super.onStart()
        setupNetwork()
    }

    override fun onResume() {
        super.onResume()
        newsFavouriteViewModel.getFavorites().observe(this.viewLifecycleOwner) { it ->
            Log.d("Error", "News fragment on resume it $it")
            it.let { newsRecyclerAdapter.updateFavourites(it) }
        }

        newsBinding.apply {
            newsDate.text = Date().getDateOrDay("dd MMM yyyy")
            newsDay.text = Date().getDateOrDay("EEEE")
        }
        newsRecyclerAdapter.onCardClick={
            val intent = Intent(context, DisplayNewsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("NewsData", it)
            intent.putExtras(bundle)
            requireContext().startActivity(intent)
        }

        newsRecyclerAdapter.onFavButtonChecked = { news ->
            newsFavouriteViewModel.checkItemExists(news.url, user)
                .observe(viewLifecycleOwner) { favourite ->
                    if (favourite == null) {
                        val newNewsFav = NewsFavourites(user, news.url)
                        newsFavouriteViewModel.addToFavourites(newNewsFav)
                    } else {
                        newsFavouriteViewModel.deleteFromFavourites(favourite)
                    }
                }
        }
        setupObservers()
    }


    private fun setupViewModel() {
        val dao = NewsFavouritesDatabase.getInstance(requireContext()).newsFavouritesDao
        newsViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), dao)
            )[NewsViewModel::class.java]
        val repository = NewsFavouriteRepository(dao)
        val factory = NewsFavouritesViewModelFactory(requireContext(), repository)
        newsFavouriteViewModel =
            ViewModelProvider(this, factory)[NewsFavouriteViewModel::class.java]

    }

    // To observe news data
    private fun setupObservers() {
        newsViewModel.getNewsData().observe(this){
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        newsBinding.newsCardRecycler.visibility = View.VISIBLE
                        newsBinding.progressBar.visibility = View.GONE
                        Log.d("News", "Success msg")
                        resource.data?.let { newsData ->
                            retrieveData(newsData = newsData.articles)
                        }
                    }
                    ERROR -> {
                        newsBinding.apply {
                            newsCardRecycler.visibility = View.VISIBLE
                            Log.d("News", "Error msg")

                            progressBar.visibility = View.GONE
                        }
                    }
                    LOADING -> {
                        newsBinding.apply {
                            Log.d("News", "Loading msg")

                            newsCardRecycler.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                        }
                    }

                }
            }
        }
    }

    private fun setupUI() {
        newsBinding.apply {
            newsCardRecycler.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            newsCardRecycler.layoutManager = LinearLayoutManager(requireContext())
            newsCardRecycler.adapter = newsRecyclerAdapter
        }
    }

    private fun setupNetwork() {
        if (ConnectivityStatus.isNetworkAvailable(requireContext())) {
            setupUI()
        } else {
            Snackbar.make(requireView(), "No internet", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun retrieveData(newsData: List<Article>) {
        newsRecyclerAdapter.apply {
            addNews(newsData)
        }
    }


}