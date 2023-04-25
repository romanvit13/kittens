package com.example.kittens.networking

import android.util.Log
import com.example.kittens.common.networkingTag
import okhttp3.Interceptor
import okhttp3.Response

class ResponseLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        Log.d(networkingTag, "Response: ${response.body?.string()}")
        return response
    }
}