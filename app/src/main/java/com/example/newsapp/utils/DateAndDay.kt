package com.example.newsapp.utils

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT="dd MMM yyyy"
const val DAY_FORMAT="EEEE"
var getLocale: Locale =Locale.getDefault()



fun getDay(): String {

    val date=Date()
    val sdf=SimpleDateFormat(DAY_FORMAT, getLocale)
    return sdf.format(date)
}
fun getDate(): String {
    val date=Date()
    val sdf=SimpleDateFormat(DATE_FORMAT, getLocale)
    return sdf.format(date)
}