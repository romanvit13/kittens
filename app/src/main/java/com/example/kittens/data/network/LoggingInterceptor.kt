package com.example.kittens.data.network

import android.util.Log
import com.example.kittens.common.networkingTag
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d(networkingTag, "Request body: ${request.body}")
        Log.d(networkingTag, "Request headers: ${request.headers}")
        Log.d(networkingTag, "Request url: ${request.url}")
        return chain.proceed(request)
    }
}