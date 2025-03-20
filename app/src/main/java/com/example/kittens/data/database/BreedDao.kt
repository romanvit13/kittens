package com.example.kittens.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.kittens.data.database.models.Breed

@Dao
interface BreedDao {
    @Query("SELECT * FROM breed")
    suspend fun getAll(): List<Breed>

    @Upsert
    suspend fun insertAll(vararg breeds: Breed)
}