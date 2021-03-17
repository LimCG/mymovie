package com.limcg.mycinema.utils

sealed class Resource<T>(open val data: T? = null, val message: String? = null) {

    class Loading<T>() : Resource<T>()

    class Success<T>(data: T) : Resource<T>(data = data)

    class Error<T>(message: String? = null) : Resource<T>(message = message)

}