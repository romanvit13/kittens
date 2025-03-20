package com.example.kittens.core

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.room.Room
import com.example.kittens.data.database.AppDatabase
import com.example.kittens.data.network.ICatService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DI {
    fun initRetrofit(): ICatService {
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
        return retrofit.create(ICatService::class.java)
    }

    fun initDatabase(applicationContext: Context): AppDatabase {
        val appDatabase =
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "kittens-database"
            ).build()

        Log.d("App", "App database was initialized: $appDatabase")
        return appDatabase
    }

    fun initMultiThreadingEnvironment(): Handler {
        return Handler(Looper.getMainLooper())
    }
}