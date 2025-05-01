package com.example.kittens.core

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val builder = Retrofit.Builder().client(client).baseUrl("https://api.thecatapi.com")
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()
        return retrofit.create(ICatService::class.java)
    }

    fun initDatabase(applicationContext: Context): AppDatabase {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create the Cat table
                db.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `Cat` (
                `id` TEXT NOT NULL,
                `url` TEXT NOT NULL,
                `width` INTEGER NOT NULL,
                `height` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
                )
            }
        }

        val appDatabase = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "kittens-database"
        ).addMigrations(MIGRATION_1_2).build()

        Log.d("App", "App database was initialized: $appDatabase")
        return appDatabase
    }

    fun initMultiThreadingEnvironment(): Handler {
        return Handler(Looper.getMainLooper())
    }
}