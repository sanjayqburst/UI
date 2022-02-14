package com.example.newsapp.utils

import com.example.newsapp.utils.Status.*

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
//    Handle success, error, and loading states
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = LOADING, data = data, message = null)
    }
}
