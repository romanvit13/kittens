package com.example.kittens.data.network

import com.example.kittens.data.network.models.Breed
import com.example.kittens.data.network.models.Cat
import retrofit2.http.GET
import retrofit2.http.Headers

interface ICatService {
    @GET("/v1/images/search?limit=20")
//    @Headers(
//        "x-api-key: live_qUArZny823UWZbNpRqW7JoYkktIfNNWIXpb4Y4Y7G8kREMoRGPcAoaDw805I16Nq",
//        "Content-Type: application/json"
//    )
    suspend fun getCatsListLimit20New(): MutableList<Cat>

    @GET("/v1/breeds")
//    @Headers(
//        "x-api-key: live_qUArZny823UWZbNpRqW7JoYkktIfNNWIXpb4Y4Y7G8kREMoRGPcAoaDw805I16Nq",
//        "Content-Type: application/json"
//    )
    suspend fun getAllBreedsNew(): MutableList<Breed>
}