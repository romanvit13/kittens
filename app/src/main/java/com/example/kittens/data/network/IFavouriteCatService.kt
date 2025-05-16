package com.example.kittens.data.network

import com.example.kittens.data.network.models.FavouriteCat
import com.example.kittens.data.network.models.FavouriteCatRequest
import com.example.kittens.data.network.models.SuccessResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IFavouriteCatService {
    @GET("/v1/favourites")
    suspend fun getFavouriteCats(): MutableList<FavouriteCat>

    @POST("/v1/favourites")
    suspend fun addFavouriteCat(@Body request: FavouriteCatRequest): Response<SuccessResponse>

    @DELETE("/v1/favourites/{favourite_id}")
    suspend fun removeFavouriteCat(@Path("favourite_id") favouriteId: Long): Response<SuccessResponse>
}