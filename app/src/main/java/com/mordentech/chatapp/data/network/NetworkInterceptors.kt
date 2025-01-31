package com.mordentech.chatapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.mordentech.chatapp.data.db.AppDatabase
import com.mordentech.chatapp.data.db.UserDao
import com.mordentech.chatapp.data.repositories.UserRepository
import com.mordentech.chatapp.util.Coroutines
import com.mordentech.chatapp.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("Make sure you have an active data connection")
        lateinit var response: Response
        try {
            response = chain.proceed(chain.request())
        } catch (e: SocketTimeoutException){
            throw NoInternetException("Make sure you have an active data connection")
        }
        return response////////// Possible SocketTimeout Exception /////////////
    }///////////////

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }
}


class AuthTokenInterceptor(
    private var db: AppDatabase
) : Interceptor {

    private suspend fun getUserToken(): String {
        return db.getUserDao().getUserToken()
    }

    private var token = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.header("No-Authentication") == null) {
            token = runBlocking { getUserToken() }
            if(!token.isNullOrEmpty()) {
                val finalToken = "Bearer $token"
                request = request.newBuilder()
                    .addHeader("Authorization", finalToken)
                    .build()
            }
        }
        return chain.proceed(request)
    }

}
