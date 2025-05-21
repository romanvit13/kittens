package com.example.kittens.domain.models

data class Cat(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<Breed>? = null,
    val favId: Long?
) {
    fun isFavourite() = favId != null
}