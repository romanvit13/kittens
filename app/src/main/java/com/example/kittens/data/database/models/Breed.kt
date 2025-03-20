package com.example.kittens.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Breed(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "temperament")
    val temperament: String,
    @ColumnInfo(name = "origin")
    val origin: String,
    @ColumnInfo("country_codes")
    val countryCodes: String,
    @ColumnInfo("country_code")
    val countryCode: String,
    @ColumnInfo("life_span")
    val lifeSpan: String?,
    @ColumnInfo("wikipedia_url")
    val wikipediaUrl: String? = "",
)
