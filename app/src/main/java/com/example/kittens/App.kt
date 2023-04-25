package com.example.kittens

import android.app.Application
import com.example.kittens.networking.ICatService
import com.example.kittens.networking.LoggingInterceptor
import com.example.kittens.networking.ResponseLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    companion object {
        var catService: ICatService? = null
    }

    override fun onCreate() {
        initRetrofit()
        super.onCreate()
    }

    private fun initRetrofit() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build()

        val builder = Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.thecatapi.com")
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()
        catService = retrofit.create(ICatService::class.java)
    }
}