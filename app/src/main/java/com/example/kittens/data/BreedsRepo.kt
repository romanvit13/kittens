package com.example.kittens.data

import com.example.kittens.common.NetworkUtils
import com.example.kittens.data.database.BreedDao
import com.example.kittens.data.network.ICatService
import com.example.kittens.domain.IBreedsRepo
import com.example.kittens.domain.models.Breed as BreedDomain

class BreedsRepo(
    private val catsService: ICatService,
    private val catsDao: BreedDao,
    private val breedMapper: BreedMapper,
    private val networkUtils: NetworkUtils) : IBreedsRepo {

    override suspend fun obtainBreeds(): MutableList<BreedDomain> {
        if (networkUtils.isNetworkAvailable()) {
            val networkBreedsList = catsService.getAllBreedsNew()
            val domainBreedsList = networkBreedsList.map { BreedDomain(it.name) }
            val databaseBreedsList = breedMapper.mapToDatabase(networkBreedsList)

            catsDao.insertAll(breeds = databaseBreedsList.toTypedArray())
            return domainBreedsList.toMutableList()
        } else {
            val databaseBreedsList = catsDao.getAll()
            val domainBreedsList = breedMapper.mapToDomain(databaseBreedsList)
            return domainBreedsList.toMutableList()
        }
    }
}