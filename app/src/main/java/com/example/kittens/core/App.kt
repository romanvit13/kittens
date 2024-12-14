package com.example.kittens.core

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.room.Room
import com.example.kittens.data.network.ICatService
import com.example.kittens.data.database.AppDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    companion object {
        var catService: ICatService? = null
        var appDatabase: AppDatabase? = null
        var mainHandler: Handler? = null
        var workHandler: Handler? = null
    }

    override fun onCreate() {
        initRetrofit()
        initDatabase()
        initMultiThreadingEnvironment()
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
        catService = null
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

    private fun initDatabase() {
        appDatabase =
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "kittens-database"
            ).build()

        Log.d("App", "App database was initialized: $appDatabase")
    }

    private fun initMultiThreadingEnvironment() {
        mainHandler = Handler(Looper.getMainLooper())
    }
}