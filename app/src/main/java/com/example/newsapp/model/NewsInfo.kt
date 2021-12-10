package com.example.newsapp.model

import android.os.Parcelable
import com.example.newsapp.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsInfo(
    var id: Int,
    var image: Int,
    var briefDesc: String,
    var fav: Boolean,
    var time: String,
    var content: String
) : Parcelable


val item1 = NewsInfo(
    1,
    R.drawable.newsimg,
    "There’s a big new presence slurping up power from the U.S." +
            " grid, and it’s growing: bitcoin miners. New research shows that the U.S. has overtaken China" +
            " as the top global destination for bitcoin mining and energy use is skyrocketing as a result.Read more...",
    true,
    "10:15AM, 17 Aug, 2021",
    "In the backdrop of rising concerns over the Omicron variant of Covid, the Maharashtra government on Tuesday announced stricter curbs on air travel for all international passengers, from both at-risk and other countries, arriving in the state and even domestic passengers flying in from other states.\n \n The curbs are over and above travel guidelines the Centre had issued a few days ago. According to the order, all international passengers arriving from at-risk countries (declared by the Centre) will have to undergo a mandatory seven-day institutional quarantine and they will be tested on the second, fourth and seventh day. If they are found to be positive, they will be shifted to a hospital and if negative, they will have to continue in home quarantine for the next seven days. The guidelines issued by the Centre had suggested a seven-day home quarantine for passengers from at-risk countries if they test negative on arrival and they were to take a retest on the eighth day.\n"
)
val item2 =
    item1.copy(id = 2, image = R.drawable.news5, fav = false, time = "12:00PM, 12 Nov, 2021")
val item3 =
    item1.copy(id = 3, image = R.drawable.news3, fav = false, time = "11:50AM, 24 Nov, 2021")
val item4 = item1.copy(id = 4, image = R.drawable.news4, time = "12:00PM, 12 Nov, 2021")
val item5 = item1.copy(id = 5, image = R.drawable.news2)
val item6 = item2.copy(id = 6, fav = true)

val dataArray = arrayListOf(item1, item2, item3, item4, item5, item6)
