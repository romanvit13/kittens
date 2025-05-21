package com.example.kittens.data.mappings

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
}