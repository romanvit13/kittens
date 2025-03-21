package com.example.kittens.data

import com.example.kittens.domain.models.Breed as BreedDomain
import com.example.kittens.data.database.models.Breed as BreedDatabase
import com.example.kittens.data.network.models.Breed as BreedNetwork

class BreedMapper {
    fun mapToDatabase(networkBreeds: List<BreedNetwork>): List<BreedDatabase> {
        return networkBreeds.map {
            BreedDatabase(
                id = it.id,
                name = it.name,
                temperament = it.temperament,
                origin = it.origin,
                countryCodes = it.countryCodes,
                countryCode = it.countryCode,
                lifeSpan = it.lifeSpan,
                wikipediaUrl = it.wikipediaUrl,
            )
        }
    }

    fun mapToDomain(databaseBreeds: List<BreedDatabase>): List<BreedDomain> {
        return databaseBreeds.map { BreedDomain(it.name) }
    }
}