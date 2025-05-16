package com.example.kittens.domain


import com.example.kittens.domain.models.Breed
import com.example.kittens.domain.models.Cat
import com.example.kittens.domain.models.FavouriteCat

interface IBreedsRepo {
    suspend fun obtainBreeds(): MutableList<Breed>?
}

interface ICatsRepo {
    suspend fun obtainCats(): MutableList<Cat>?
}

interface IFavouriteCatsRepo {

    suspend fun addFavouriteCat(
        imageId: String,
        subId: String? = null
    ): Result<Any>

    suspend fun removeFavouriteCat(
        favouriteId: Long
    ): Result<String> // message like "SUCCESS" or error

    suspend fun getFavouriteCats(): Result<List<FavouriteCat>>
}