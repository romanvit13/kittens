package com.example.kittens.data.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    primaryKeys = ["catId", "breedId"],
    foreignKeys = [
        ForeignKey(
            entity = Cat::class,
            parentColumns = ["id"],
            childColumns = ["catId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Breed::class,
            parentColumns = ["id"],
            childColumns = ["breedId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("breedId")]
)
data class CatBreedCrossRef(
    val catId: String,
    val breedId: String
)

data class CatWithBreeds(
    @Embedded val cat: Cat,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CatBreedCrossRef::class,
            parentColumn = "catId",
            entityColumn = "breedId"
        )
    )
    val breeds: List<Breed>
)

data class BreedWithCats(
    @Embedded val breed: Breed,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CatBreedCrossRef::class,
            parentColumn = "breedId",
            entityColumn = "catId"
        )
    )
    val cats: List<Cat>
)