package com.example.kittens.room.models

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BreedDao {
    @Query("SELECT * FROM breed")
    fun getAll(): List<Breed>

    @Upsert
    fun insertAll(vararg breeds: Breed)
}