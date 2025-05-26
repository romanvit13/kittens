package com.example.kittens.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kittens.data.database.models.Breed
import com.example.kittens.data.database.models.Cat
import com.example.kittens.data.database.models.CatBreedCrossRef
import com.example.kittens.data.database.models.FavouriteCat

@Database(
    entities = [Breed::class, Cat::class, CatBreedCrossRef::class, FavouriteCat::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun catDao(): CatDao
    abstract fun favouriteCatDao(): FavouriteCatDao
}