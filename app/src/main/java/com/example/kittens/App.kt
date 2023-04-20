package com.example.kittens

import android.app.Application
import com.example.kittens.networking.ICatService
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
        val builder = Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com")
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()
        catService = retrofit.create(ICatService::class.java)
    }
}