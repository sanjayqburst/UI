package com.example.newsapp.model

import com.example.newsapp.R

data class NewsInfo(var image:Int,var briefDesc:String,var fav:Boolean,var time:String)



val item1=NewsInfo(R.drawable.newsimg,
    "There’s a big new presence slurping up power from the U.S." +
            " grid, and it’s growing: bitcoin miners. New research shows that the U.S. has overtaken China" +
            " as the top global destination for bitcoin mining and energy use is skyrocketing as a result.Read more...",
true,"10:15AM, 17 Aug, 2021")
val item2= item1.copy(fav = false, time ="12:00PM, 12 Nov, 2021" )
val item3= item1.copy(fav = false, time = "11:50AM, 24 Nov, 2021")
val item4= item1.copy(time ="12:00PM, 12 Nov, 2021" )
val item5= item1.copy()
val item6= item2.copy(fav = true)
val dataArray= arrayOf(item1, item2,item3,item4,item5,item6)