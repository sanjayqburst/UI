package com.example.newsapp.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.RecyclerViewAdapter
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.utils.getDate
import com.example.newsapp.utils.getDay
import java.text.SimpleDateFormat
import java.util.*


class NewsFragment : Fragment() {
    private lateinit var newLayoutManager:LinearLayoutManager
    private lateinit var newsBinding: FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsBinding= FragmentNewsBinding.inflate(layoutInflater)

    }
    private val urlLinks= arrayOf("https://regmedia.co.uk/2021/11/26/shutterstock_smart_home.jpg",
    "https://bsmedia.business-standard.com/_media/bs/img/article/2021-03/22/full/1616402274-9851.jpg",
    "https://c.ndtvimg.com/2021-11/vr60k2ko_tesla_625x300_25_November_21.png",
    "https://ksltv.com/wp-content/uploads/2021/11/image00005-1.jpeg",
    "https://images.firstpost.com/wp-content/uploads/2021/11/bmw-seeks-temporary-import-duty-cuts-on-electric-vehicles-in-india.jpg",
    "https://www.irishtimes.com/image-creator/?id=1.4741598&origw=1440")
    private val images= arrayOf(R.drawable.newsimg,R.drawable.newsimg,R.drawable.newsimg,R.drawable.newsimg,R.drawable.newsimg,R.drawable.newsimg)
    private val newsTime= arrayOf("10:15AM, 17 Aug, 2021","12:00PM, 12 Nov, 2021","11:50AM, 24 Nov, 2021","12:00PM, 12 Nov, 2021","10:15AM, 17 Aug, 2021","09:00PM, 08 Sep, 2021")
    private val briefDesc= arrayOf("There’s a big new presence slurping up power from the U.S. grid, and it’s growing: bitcoin miners. New research shows that the U.S. has overtaken China as the top global destination for bitcoin mining and energy use is skyrocketing as a result.Read more...","Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ","Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ","Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ","Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ","Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ")
    private val favType= arrayOf(true,false,false,true,true,false)
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
        val newsRecycleAdapter=RecyclerViewAdapter(images.size,images, briefDesc,favType,newsTime)
        newsBinding.newsCardRecycler.adapter=newsRecycleAdapter



        newsBinding.apply {
            newsDate.text= getDate()
            newsDay.text= getDay()
        }
    }


}