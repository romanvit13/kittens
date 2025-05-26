package com.example.kittens.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kittens.data.database.models.FavouriteCat

@Dao
interface FavouriteCatDao {
    @Query("SELECT * FROM FavouriteCat")
    suspend fun getFavouriteCats(): List<FavouriteCat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteCats(favouriteCats: List<FavouriteCat>)

    @Query("DELETE FROM FavouriteCat")
    suspend fun removeAllFavouriteCats()
}