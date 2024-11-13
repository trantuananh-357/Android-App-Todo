package com.example.android_todoapp.extension

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.util.Locale

fun getContextWithResources(
    context: Context?, language: String?,
): Context? {
    if (language.isNullOrEmpty()) return context
    val locale = Locale(language)
    Locale.setDefault(locale)
    val res = context?.resources
    val config = Configuration(res?.configuration)
    config.setLocale(locale)
    return context?.createConfigurationContext(config)
}
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(ConnectivityManager::class.java)

    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}
