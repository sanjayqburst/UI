package com.example.newsapp.utils

import java.text.SimpleDateFormat
import java.util.*


fun Date.getDateOrDay(format:String,locale: Locale= Locale.getDefault()):String{
    val sdf=SimpleDateFormat(format, locale)
    return sdf.format(this)
}
