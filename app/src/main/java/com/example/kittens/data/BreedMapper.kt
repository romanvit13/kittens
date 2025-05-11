package com.example.kittens.data

import com.example.kittens.data.database.models.Breed as BreedDatabase
import com.example.kittens.data.network.models.Breed as BreedNetwork
import com.example.kittens.domain.models.Breed as BreedDomain

class BreedMapper {

    fun mapNetworkToDatabase(networkBreeds: List<BreedNetwork>): List<BreedDatabase> {
        return networkBreeds.map { mapNetworkToDatabase(it) }
    }

    fun mapNetworkToDatabase(networkBreed: BreedNetwork): BreedDatabase {
        return BreedDatabase(
            id = networkBreed.id,
            name = networkBreed.name,
            temperament = networkBreed.temperament,
            origin = networkBreed.origin,
            countryCodes = networkBreed.countryCodes,
            countryCode = networkBreed.countryCode,
            lifeSpan = networkBreed.lifeSpan,
            wikipediaUrl = networkBreed.wikipediaUrl
        )
    }

    fun mapDatabaseToDomain(databaseBreeds: List<BreedDatabase>): List<BreedDomain> {
        return databaseBreeds.map { mapDatabaseToDomain(it) }
    }

    fun mapDatabaseToDomain(breed: BreedDatabase): BreedDomain {
        return BreedDomain(
            id = breed.id,
            name = breed.name,
            temperament = breed.temperament,
            origin = breed.origin,
            countryCodes = breed.countryCodes,
            countryCode = breed.countryCode,
            lifeSpan = breed.lifeSpan,
            wikipediaUrl = breed.wikipediaUrl
        )
    }

    fun mapNetworkToDomain(networkBreeds: List<BreedNetwork>): List<BreedDomain> {
        return networkBreeds.map { mapNetworkToDomain(it) }
    }

    fun mapNetworkToDomain(breed: BreedNetwork): BreedDomain {
        return BreedDomain(
            id = breed.id,
            name = breed.name,
            temperament = breed.temperament,
            origin = breed.origin,
            countryCodes = breed.countryCodes,
            countryCode = breed.countryCode,
            lifeSpan = breed.lifeSpan,
            wikipediaUrl = breed.wikipediaUrl
        )
    }
}