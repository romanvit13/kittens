package com.example.kittens.models

data class Breed(
    val id: Int,
    val name: String,
    val temperament: List<String>,
    val origin: String,
    val lifeSpan: String,
    val wikipediaUrl: String,
)