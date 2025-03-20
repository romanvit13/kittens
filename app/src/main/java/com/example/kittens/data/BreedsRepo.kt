package com.example.kittens.data

import com.example.kittens.core.App
import com.example.kittens.data.network.ICatService
import com.example.kittens.domain.IBreedsRepo
import com.example.kittens.domain.models.Breed as BreedDomain
import com.example.kittens.data.database.models.Breed as BreedDatabase
import com.example.kittens.data.network.models.Breed as BreedNetwork

class BreedsRepo(val catsService: ICatService) : IBreedsRepo {
    private var breedsList: List<BreedDomain>? = null

    override suspend fun obtainBreeds(): MutableList<BreedDomain> {
        val networkBreedsList = catsService.getAllBreedsNew()
        val domainBreedsList = networkBreedsList.map { BreedDomain(it.name) }
        val databaseBreedsList = networkBreedsList.mapToBreedDatabase()

        saveBreedsToDb(databaseBreedsList)
        return domainBreedsList.toMutableList()
    }

    suspend fun loadBreedsFromDb() {
        var breeds: List<BreedDatabase>? = null
        breeds = App.appDatabase?.breedDao()?.getAll()
    }

    private suspend fun saveBreedsToDb(breeds: List<BreedDatabase>) {
        App.appDatabase?.breedDao()?.insertAll(breeds = breeds.toTypedArray())
    }
}

fun List<BreedNetwork>.mapToBreedDatabase(): List<BreedDatabase> {
    return this.map {
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