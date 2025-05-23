package com.example.kittens.data.repos

import com.example.kittens.common.NetworkUtils
import com.example.kittens.data.database.CatDao
import com.example.kittens.data.mappings.CatsMapper
import com.example.kittens.data.network.ICatService
import com.example.kittens.data.network.IFavouriteCatService
import com.example.kittens.domain.ICatsRepo
import com.example.kittens.domain.models.Cat

class CatsRepo(
    private val catsService: ICatService,
    private val favouriteCatsService: IFavouriteCatService,
    private val catsDao: CatDao,
    private val catsMapper: CatsMapper,
    private val networkUtils: NetworkUtils
) : ICatsRepo {
    override suspend fun obtainCats(): MutableList<Cat> {
        if (networkUtils.isNetworkAvailable()) {
            val networkCatsList = catsService.getCatsListLimit20New()
            val favouriteCatsList = favouriteCatsService.getFavouriteCats()
            val databaseCatsList = catsMapper.mapNetworkToDatabase(networkCatsList)
            val domainCatsList =
                catsMapper.mapNetworkToDomainAggregated(networkCatsList, favouriteCatsList)

            catsDao.deleteAllCats()
            catsDao.insertCats(databaseCatsList.cats)
            return domainCatsList.toMutableList()
        } else {
            val databaseCatsList = catsDao.getCatsWithBreeds()
            val domainCatsList = catsMapper.mapCatsWithBreedsToDomain(databaseCatsList)
            return domainCatsList.toMutableList()
        }
    }
}