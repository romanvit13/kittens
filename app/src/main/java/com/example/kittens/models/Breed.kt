package com.example.kittens.models

import com.google.gson.annotations.SerializedName


data class Breed(
    val weight: Weight,
    val id: Int,
    val name: String,
    val temperament: List<String>,
    val origin: String,
    @SerializedName("country_codes")
    val countryCodes: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("life_span")
    val lifeSpan: String,
    @SerializedName("wikipedia_url")
    val wikipediaUrl: String,
)