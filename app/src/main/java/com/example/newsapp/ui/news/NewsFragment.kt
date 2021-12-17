package com.example.newsapp.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapters.NewsRecyclerAdapter
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsData
import com.example.newsapp.network.RetrofitUtil
import com.example.newsapp.utils.getDateOrDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class NewsFragment : Fragment() {
    private lateinit var newLayoutManager: LinearLayoutManager
    private lateinit var newsBinding: FragmentNewsBinding
    private lateinit var dataArrayNews: List<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsBinding = FragmentNewsBinding.inflate(layoutInflater)
        Log.d("Msg", "onCreate news")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("Msg", "onCreateView")
        Log.d("Msg", "onViewCreated")
        return newsBinding.root
    }

    override fun onResume() {
        super.onResume()
        val api = RetrofitUtil.headlines
//        TODO : seperate api through callback function
        api.clone().enqueue(object : Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                Log.d("Msg", "onResponse")
                Log.d("Msg", "Response ${response.body()}")
                dataArrayNews = response.body()!!.articles
                newLayoutManager = LinearLayoutManager(requireContext())
                newsBinding.newsCardRecycler.layoutManager = newLayoutManager
                val newsRecyclerAdapter = NewsRecyclerAdapter(requireContext(), dataArrayNews)
                newsBinding.newsCardRecycler.adapter = newsRecyclerAdapter
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                Log.d("Msg", "onViewCreated")
            }
        })
        newsBinding.apply {
            newsDate.text = Date().getDateOrDay("dd MMM yyyy")
            newsDay.text = Date().getDateOrDay("EEEE")
        }
    }


}