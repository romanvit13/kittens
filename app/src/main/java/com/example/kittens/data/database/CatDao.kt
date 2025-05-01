package com.example.kittens.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.kittens.data.database.models.Cat

@Dao
interface CatDao {
    @Query("SELECT * FROM cat")
    suspend fun getAll(): List<Cat>

    @Upsert
    suspend fun insertAll(vararg cats: Cat)
}