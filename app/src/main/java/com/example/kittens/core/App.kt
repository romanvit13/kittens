package com.example.kittens.core

import android.app.Application
import com.example.kittens.core.modules.databaseModule
import com.example.kittens.core.modules.networkModule
import com.example.kittens.core.modules.repositoryModule
import com.example.kittens.core.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            modules(
                viewModelModule,
                networkModule,
                databaseModule,
                repositoryModule,
            )
        }
    }
}