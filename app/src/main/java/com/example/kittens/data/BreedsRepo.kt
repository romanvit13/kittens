package com.example.kittens.data

import com.example.kittens.core.App
import com.example.kittens.data.network.ICatService
import com.example.kittens.domain.IBreedsRepo
import com.example.kittens.domain.models.Breed as BreedDomain

class BreedsRepo(val catsService: ICatService): IBreedsRepo {
    private var breedsList: List<BreedDomain>? = null

    override suspend fun obtainBreeds(): MutableList<BreedDomain> {
        val domainBreedsList = catsService.getAllBreedsNew().map { BreedDomain(it.name) }
        return domainBreedsList.toMutableList()
    }

    fun loadBreedsFromDb() {
        var breeds: List<com.example.kittens.data.database.models.Breed>? = null
        val runnable = {
            breeds = App.appDatabase?.breedDao()?.getAll()
        }
        Thread(runnable).start()
    }
}