package com.example.kittens.data

import com.example.kittens.domain.models.Cat as DomainCat
import com.example.kittens.data.network.models.Cat as NetworkCat
import com.example.kittens.data.database.models.Cat as DatabaseCat

class CatsMapper {
    fun mapNetworkToDatabase(networkCats: List<NetworkCat>): List<DatabaseCat> {
        return networkCats.map {
            DatabaseCat(
                it.id,
                it.url,
                it.width,
                it.height,
            )
        }
    }

    fun mapNetworkToDomain(networkCats: List<NetworkCat>): List<DomainCat> {
        return networkCats.map {
            DomainCat(
                it.id,
                it.url,
                it.width,
                it.height,
            )
        }
    }

    fun mapDatabaseToDomain(databaseCats: List<DatabaseCat>): List<DomainCat> {
        return databaseCats.map {
            DomainCat(
                it.id,
                it.url,
                it.width,
                it.height,
            )
        }
    }
}