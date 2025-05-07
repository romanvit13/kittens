package com.example.kittens.core.modules

import com.example.kittens.common.NetworkUtils
import com.example.kittens.data.network.ICatService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val CATS_URL = "https://api.thecatapi.com"

val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val builder = Retrofit.Builder().client(client).baseUrl(CATS_URL)
            .addConverterFactory(GsonConverterFactory.create())
        builder.build()
    }

    single {
        get<Retrofit>().create(ICatService::class.java)
    }

    single { NetworkUtils(get()) }
}