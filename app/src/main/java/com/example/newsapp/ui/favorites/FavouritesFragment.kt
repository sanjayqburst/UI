package com.example.newsapp.ui.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.FavoriteRecyclerAdapter
import com.example.newsapp.databinding.FragmentFavouritesBinding
import com.example.newsapp.model.NewsInfo
import com.example.newsapp.model.dataArray


class FavouritesFragment : Fragment() {
    private lateinit var favouritesBinding: FragmentFavouritesBinding
    private lateinit var newLayoutManager: GridLayoutManager
    private lateinit var arrayFav:List<Int>
    private lateinit var favSharedPreference:FavSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favSharedPreference= FavSharedPreference(requireContext())
        Log.d("Hello",favSharedPreference.getKeys().toString())
        favouritesBinding= FragmentFavouritesBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return favouritesBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        newLayoutManager= GridLayoutManager(requireContext(),1)
        favouritesBinding.favoriteCardRecycler.layoutManager=newLayoutManager
        val favSharedPreference=FavSharedPreference(requireContext())
        val array= favSharedPreference.getKeys()
        val intArray=array.toTypedArray()
        val newArr:List<NewsInfo> = dataArray.filter { it.id.toString() in intArray}
        val favoriteRecyclerAdapter=FavoriteRecyclerAdapter(requireContext(),newArr.size, newArr.toMutableList())
        favouritesBinding.favoriteCardRecycler.adapter=favoriteRecyclerAdapter

    }
}