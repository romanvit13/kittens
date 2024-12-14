package com.example.kittens.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kittens.data.database.models.Breed

@Database(entities = [Breed::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
}