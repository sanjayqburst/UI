package com.example.newsapp.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapters.NewsRecyclerAdapter
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.dataArray
import com.example.newsapp.utils.getDate
import com.example.newsapp.utils.getDay


class NewsFragment : Fragment() {
    private lateinit var newLayoutManager:LinearLayoutManager
    private lateinit var newsBinding: FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsBinding= FragmentNewsBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return newsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newLayoutManager= GridLayoutManager(requireContext(),1)
        newsBinding.newsCardRecycler.layoutManager=newLayoutManager
        val newsRecyclerAdapter=NewsRecyclerAdapter(requireContext(),dataArray.size, dataArray)
        newsBinding.newsCardRecycler.adapter=newsRecyclerAdapter

        newsBinding.apply {
            newsDate.text= getDate()
            newsDay.text= getDay()
        }


    }


}