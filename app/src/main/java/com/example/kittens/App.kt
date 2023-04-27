package com.example.kittens

import android.app.Application
import com.example.kittens.networking.ICatService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val builder = Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.thecatapi.com")
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()
        catService = retrofit.create(ICatService::class.java)
    }
}