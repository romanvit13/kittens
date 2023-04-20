package com.example.kittens.networking

import com.example.kittens.models.Cat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ICatService {
    @GET("/v1/images/search?limit=20")
    @Headers("x-api-key: fa0e4933-565b-4e42-a44d-dc1d45cca980", "Content-Type: application/json")
    fun getCatsListLimit20(): Call<List<Cat>>
}