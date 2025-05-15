package com.example.kittens.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "x-api-key",
                "live_qUArZny823UWZbNpRqW7JoYkktIfNNWIXpb4Y4Y7G8kREMoRGPcAoaDw805I16Nq"
            )
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}