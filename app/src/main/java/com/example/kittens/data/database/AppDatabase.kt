package com.example.kittens.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kittens.data.database.models.Breed
import com.example.kittens.data.database.models.Cat
import com.example.kittens.data.database.models.CatBreedCrossRef

@Database(
    entities = [Breed::class, Cat::class, CatBreedCrossRef::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun catDao(): CatDao
}