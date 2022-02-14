package com.example.newsapp.ui.main.favorites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentFavouritesBinding
import com.example.newsapp.db.NewsFavouriteRepository
import com.example.newsapp.db.NewsFavouriteViewModel
import com.example.newsapp.db.NewsFavouritesDatabase
import com.example.newsapp.db.NewsFavouritesViewModelFactory
import com.example.newsapp.model.Article
import com.example.newsapp.network.ConnectivityStatus
import com.example.newsapp.network.api.ApiHelper
import com.example.newsapp.network.api.RetrofitBuilder
import com.example.newsapp.ui.base.ViewModelFactory
import com.example.newsapp.ui.main.DisplayNewsActivity
import com.example.newsapp.ui.main.accounts.UserSharedPreference
import com.example.newsapp.ui.main.adapters.FavoriteRecyclerAdapter
import com.example.newsapp.ui.main.viewmodel.NewsViewModel
import com.example.newsapp.utils.Status
import com.google.android.material.snackbar.Snackbar


class FavouritesFragment : Fragment() {
    private lateinit var favouritesBinding: FragmentFavouritesBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var favoriteRecyclerAdapter: FavoriteRecyclerAdapter
    private lateinit var newsFavouriteViewModel: NewsFavouriteViewModel
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favouritesBinding = FragmentFavouritesBinding.inflate(layoutInflater)
        user = UserSharedPreference(requireContext()).getValue("username")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favouritesBinding.swipeRefresh.setOnRefreshListener {
            setupNetwork()
            favouritesBinding.swipeRefresh.isRefreshing = false
        }
        return favouritesBinding.root
    }

    override fun onStart() {
        super.onStart()
        setupViewModel()
        setupNetwork()
    }

    override fun onPause() {
        super.onPause()
        viewModelStore.clear()
    }

    override fun onResume() {
        super.onResume()
        favoriteRecyclerAdapter.onCardClick = {
            val intent = Intent(context, DisplayNewsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("NewsData", it)
            intent.putExtras(bundle)
            requireContext().startActivity(intent)
        }

        favoriteRecyclerAdapter.onFavButtonChecked = { news ->
            try {
                newsFavouriteViewModel.checkItemExists(news.url, user)
                    .observe(viewLifecycleOwner) { favourite ->
                        newsFavouriteViewModel.deleteFromFavourites(favourite!!)
                    }
                newsViewModel.getFavData(user).observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.ERROR -> {
                                favouritesBinding.apply {
                                    favoriteCardRecycler.visibility = View.VISIBLE
                                    progressBarFav.visibility = View.GONE
                                }
                            }
                            Status.SUCCESS -> {
                                favouritesBinding.favoriteCardRecycler.visibility = View.VISIBLE
                                favouritesBinding.progressBarFav.visibility = View.GONE
                                resource.data?.let { favData ->
                                    updateData(newsData = favData)

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
                }
            } catch (e: Exception) {
                Snackbar.make(requireView(), "Could not remove fragment", Snackbar.LENGTH_SHORT)
                    .show()
                Log.e("Error fav fragment", "Error ${e.localizedMessage}")
            }

        }
        setupObservers()

    }

    private fun setupViewModel() {
        val dao = NewsFavouritesDatabase.getInstance(requireContext()).newsFavouritesDao
        try {
            newsViewModel =ViewModelProvider(
                this,
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), dao)
            )[NewsViewModel::class.java]
        }catch (e:Exception){
            Log.e("Error","error ${e.localizedMessage}")
        }
        val repository = NewsFavouriteRepository(dao)
        val factory = NewsFavouritesViewModelFactory(user, repository)
        newsFavouriteViewModel =
            ViewModelProvider(requireActivity(), factory)[NewsFavouriteViewModel::class.java]
    }

    private fun setupObservers() {
        newsViewModel.getFavData(user).observe(this) {
            it?.let { resource ->
                when (resource.status) {
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
                    Status.SUCCESS -> {
                        favouritesBinding.favoriteCardRecycler.visibility = View.VISIBLE
                        favouritesBinding.progressBarFav.visibility = View.GONE
                        resource.data?.let { favData ->
                            updateData(newsData = favData)

                        }
                    }
                }
            }
        }
    }

    private fun setupUI() {
        favouritesBinding.apply {
            favoriteCardRecycler.visibility = View.VISIBLE
            progressBarFav.visibility = View.VISIBLE
            favoriteCardRecycler.layoutManager = LinearLayoutManager(requireContext())
            favoriteRecyclerAdapter = FavoriteRecyclerAdapter(arrayListOf())
            favoriteCardRecycler.adapter = favoriteRecyclerAdapter
        }
    }

    private fun setupNetwork() {
        if (ConnectivityStatus.isNetworkAvailable(requireContext())) {
            setupUI()
        } else {
            Snackbar.make(favouritesBinding.root, "No internet", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun updateData(newsData: List<Article>) {
        favoriteRecyclerAdapter.apply {
            addNews(newsData)
        }
    }

}