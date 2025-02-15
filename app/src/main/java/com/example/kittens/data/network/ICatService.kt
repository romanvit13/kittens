package com.example.kittens.data.network

import com.example.kittens.data.network.models.Breed
import com.example.kittens.data.network.models.Cat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ICatService {
    @GET("/v1/images/search?limit=20")
    @Headers("x-api-key: fa0e4933-565b-4e42-a44d-dc1d45cca980", "Content-Type: application/json")
    fun getCatsListLimit20(): Call<MutableList<Cat>>

    @GET("/v1/breeds")
    @Headers("x-api-key: fa0e4933-565b-4e42-a44d-dc1d45cca980", "Content-Type: application/json")
    fun getAllBreeds(): Call<MutableList<Breed>>

    @GET("/v1/images/search?limit=20")
    @Headers("x-api-key: fa0e4933-565b-4e42-a44d-dc1d45cca980", "Content-Type: application/json")
    suspend fun getCatsListLimit20New(): MutableList<Cat>

    @GET("/v1/breeds")
    @Headers("x-api-key: fa0e4933-565b-4e42-a44d-dc1d45cca980", "Content-Type: application/json")
    suspend fun getAllBreedsNew(): MutableList<Breed>
}