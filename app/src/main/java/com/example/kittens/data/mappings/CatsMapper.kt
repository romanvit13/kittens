package com.example.kittens.data.mappings

import com.example.kittens.data.database.models.Cat as DatabaseCat
import com.example.kittens.data.database.models.Breed as BreedDatabase
import com.example.kittens.data.database.models.CatBreedCrossRef
import com.example.kittens.data.database.models.CatWithBreeds
import com.example.kittens.data.network.models.FavouriteCat as NetworkFavouriteCat
import com.example.kittens.data.network.models.Cat as NetworkCat
import com.example.kittens.domain.models.Cat as DomainCat

class CatsMapper(private val breedMapper: BreedMapper) {

    data class CatDatabaseBundle(
        val cats: List<DatabaseCat>,
        val breeds: List<BreedDatabase>,
        val crossRefs: List<CatBreedCrossRef>
    )

    fun mapNetworkToDomainAggregated(
        networkCats: List<NetworkCat>,
        favouriteCats: List<NetworkFavouriteCat>
    ): List<DomainCat> {
        val favouriteMap = favouriteCats.associateBy { it.imageId }

        return networkCats.map { cat ->
            val favourite = favouriteMap[cat.id]
            DomainCat(
                id = cat.id,
                url = cat.url,
                width = cat.width,
                height = cat.height,
                favId = favourite?.id
            )
        }
    }

    fun mapNetworkToDatabase(networkCats: List<NetworkCat>): CatDatabaseBundle {
        val cats = mutableListOf<DatabaseCat>()
        val allBreeds = mutableMapOf<String, BreedDatabase>()
        val crossRefs = mutableListOf<CatBreedCrossRef>()

        for (networkCat in networkCats) {
            cats.add(
                DatabaseCat(
                    id = networkCat.id,
                    url = networkCat.url,
                    width = networkCat.width,
                    height = networkCat.height
                )
            )

            networkCat.breeds?.forEach { networkBreed ->
                allBreeds[networkBreed.id] = breedMapper.mapNetworkToDatabase(networkBreed)

                crossRefs.add(
                    CatBreedCrossRef(
                        catId = networkCat.id,
                        breedId = networkBreed.id
                    )
                )
            }
        }

        return CatDatabaseBundle(
            cats = cats,
            breeds = allBreeds.values.toList(),
            crossRefs = crossRefs
        )
    }

    fun mapNetworkToDomain(networkCats: List<NetworkCat>): List<DomainCat> {
        return networkCats.map { networkCat ->
            DomainCat(
                id = networkCat.id,
                url = networkCat.url,
                width = networkCat.width,
                height = networkCat.height,
                breeds = networkCat.breeds?.let { breedMapper.mapNetworkToDomain(it) },
                favId = null,
            )
        }
    }

    fun mapCatsWithBreedsToDomain(catsWithBreeds: List<CatWithBreeds>): List<DomainCat> {
        return catsWithBreeds.map { catWithBreeds ->
            DomainCat(
                id = catWithBreeds.cat.id,
                url = catWithBreeds.cat.url,
                width = catWithBreeds.cat.width,
                height = catWithBreeds.cat.height,
                breeds = breedMapper.mapDatabaseToDomain(catWithBreeds.breeds),
                favId = null,
            )
        }
    }
}