package com.example.kittens.domain.models

data class FavouriteCat(
    val id: Long, // favourite_id from the API
    val imageId: String,
    val imageUrl: String,
    val createdAt: String,
)