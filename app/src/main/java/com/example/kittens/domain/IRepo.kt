package com.example.kittens.domain


import com.example.kittens.domain.models.Breed
import com.example.kittens.domain.models.Cat

interface IBreedsRepo {
    suspend fun obtainBreeds(): MutableList<Breed>?
}

interface ICatsRepo {
    suspend fun obtainCats(): MutableList<Cat>?
}