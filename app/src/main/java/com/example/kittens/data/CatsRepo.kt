package com.example.kittens.data

import com.example.kittens.common.NetworkUtils
import com.example.kittens.data.database.CatDao
import com.example.kittens.data.network.ICatService
import com.example.kittens.domain.ICatsRepo
import com.example.kittens.domain.models.Cat

class CatsRepo(
    private val catsService: ICatService,
    private val catsDao: CatDao,
    private val catsMapper: CatsMapper,
    private val networkUtils: NetworkUtils
) : ICatsRepo {
    override suspend fun obtainCats(): MutableList<Cat> {
        if (networkUtils.isNetworkAvailable()) {
            val networkCatsList = catsService.getCatsListLimit20New()
            val databaseCatsList = catsMapper.mapNetworkToDatabase(networkCatsList)
            val domainCatsList = catsMapper.mapNetworkToDomain(networkCatsList)

            catsDao.insertAll(cats = databaseCatsList.toTypedArray())
            return domainCatsList.toMutableList()
        } else {
            val databaseCatsList = catsDao.getAll()
            val domainCatsList = catsMapper.mapDatabaseToDomain(databaseCatsList)
            return domainCatsList.toMutableList()
        }
    }
}