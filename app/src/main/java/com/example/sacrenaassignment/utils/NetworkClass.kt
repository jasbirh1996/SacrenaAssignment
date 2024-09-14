package com.example.sacrenaassignment.utils

sealed class NetworkClass<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T? = null, message: String? = null) : NetworkClass<T>(data, message)
    class Loading<T>(data: T? = null) : NetworkClass<T>(data)
    class Error<T>(data: T? = null, message: String? = null) : NetworkClass<T>(data, message)
}