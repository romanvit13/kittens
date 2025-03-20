package com.example.kittens.core

import android.app.Application
import android.os.Handler
import com.example.kittens.data.network.ICatService
import com.example.kittens.data.database.AppDatabase


class App : Application() {
    companion object {
        var catService: ICatService? = null
        var appDatabase: AppDatabase? = null
        var mainHandler: Handler? = null
        var workHandler: Handler? = null
    }

    override fun onCreate() {
        val di = DI()
        catService = di.initRetrofit()
        appDatabase = di.initDatabase(this)
        mainHandler = di.initMultiThreadingEnvironment()
        super.onCreate()
    }

    override fun onTerminate() {
        catService = null
        super.onTerminate()
    }
}