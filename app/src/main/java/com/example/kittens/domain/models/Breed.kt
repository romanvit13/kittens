package com.example.kittens.domain.models

data class Breed(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val countryCodes: String,
    val countryCode: String,
    val lifeSpan: String?,
    val wikipediaUrl: String?,
)
