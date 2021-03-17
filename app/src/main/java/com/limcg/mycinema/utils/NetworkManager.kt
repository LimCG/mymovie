package com.limcg.mycinema.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkManager {

    fun isConnected(context : Context) : Boolean
    {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}