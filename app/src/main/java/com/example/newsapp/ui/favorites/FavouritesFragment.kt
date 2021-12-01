package com.example.newsapp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.FavoriteRecyclerAdapter
import com.example.newsapp.databinding.FragmentFavouritesBinding
import com.example.newsapp.model.dataArray


class FavouritesFragment : Fragment() {
    private lateinit var favouritesBinding: FragmentFavouritesBinding
    private lateinit var newLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favouritesBinding= FragmentFavouritesBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return favouritesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newLayoutManager= GridLayoutManager(requireContext(),1)
        favouritesBinding.favoriteCardRecycler.layoutManager=newLayoutManager
        val newArr= dataArray.filter { it.fav }
        val favoriteRecyclerAdapter=FavoriteRecyclerAdapter(requireContext(),newArr.size, newArr.toTypedArray())
        favouritesBinding.favoriteCardRecycler.adapter=favoriteRecyclerAdapter
    }


}