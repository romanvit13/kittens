package com.example.kittens.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.kittens.data.database.models.Breed
import com.example.kittens.data.database.models.Cat
import com.example.kittens.data.database.models.CatBreedCrossRef
import com.example.kittens.data.database.models.CatWithBreeds

@Dao
interface CatDao {
    @Transaction
    @Query("SELECT * FROM Cat")
    suspend fun getCatsWithBreeds(): List<CatWithBreeds>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(cat: Cat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCats(cats: List<Cat>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreeds(breeds: List<Breed>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRefs(crossRefs: List<CatBreedCrossRef>)

    @Query("DELETE FROM Cat")
    suspend fun deleteAllCats()

    @Query("DELETE FROM Breed")
    suspend fun deleteAllBreeds()

    @Query("DELETE FROM CatBreedCrossRef")
    suspend fun deleteAllCrossRefs()

    @Transaction
    suspend fun refreshCatsAndBreeds(
        cats: List<Cat>,
        breeds: List<Breed>,
        crossRefs: List<CatBreedCrossRef>
    ) {
        deleteAllCrossRefs()
        deleteAllBreeds()
        deleteAllCats()

        insertCats(cats)
        insertBreeds(breeds)
        insertCrossRefs(crossRefs)
    }
}