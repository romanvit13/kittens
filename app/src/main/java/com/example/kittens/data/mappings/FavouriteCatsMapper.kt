package com.example.kittens.data.mappings

import com.example.kittens.data.database.models.FavouriteCat as FavouriteCatDatabase
import com.example.kittens.data.network.models.FavouriteCat as FavouriteCatNetwork
import com.example.kittens.domain.models.FavouriteCat as FavouriteCatDomain

class FavouriteCatsMapper {
    fun mapNetworkToDomain(favouriteCat: FavouriteCatNetwork): FavouriteCatDomain {
        return FavouriteCatDomain(
            favouriteCat.id,
            favouriteCat.imageId,
            favouriteCat.image.url,
            favouriteCat.createdAt
        )
    }

    fun mapNetworkToDomain(favouriteCats: List<FavouriteCatNetwork>): List<FavouriteCatDomain> {
        return favouriteCats.map { mapNetworkToDomain(it) }
    }

    fun mapNetworkToDatabase(favouriteCat: FavouriteCatNetwork): FavouriteCatDatabase {
        return FavouriteCatDatabase(
            favouriteCat.id,
            favouriteCat.userId,
            favouriteCat.imageId,
            favouriteCat.subId,
            favouriteCat.createdAt,
            favouriteCat.image.url,
        )
    }

    fun mapNetworkToDatabase(favouriteCats: List<FavouriteCatNetwork>): List<FavouriteCatDatabase> {
        return favouriteCats.map { mapNetworkToDatabase(it) }
    }

    fun mapDatabaseToDomain(favouriteCat: FavouriteCatDatabase) : FavouriteCatDomain {
        return FavouriteCatDomain(
            favouriteCat.id,
            favouriteCat.imageId,
            favouriteCat.imageUrl,
            favouriteCat.createdAt
        )
    }

    fun mapDatabaseToDomain(favouriteCats: List<FavouriteCatDatabase>): List<FavouriteCatDomain> {
        return favouriteCats.map { mapDatabaseToDomain(it) }
    }

}