package com.example.kittens.room.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Breed::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
}