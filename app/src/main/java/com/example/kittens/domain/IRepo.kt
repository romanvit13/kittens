package com.example.kittens.domain

import com.example.kittens.domain.models.Breed

interface IBreedsRepo {
    suspend fun obtainBreeds(): MutableList<Breed>
}