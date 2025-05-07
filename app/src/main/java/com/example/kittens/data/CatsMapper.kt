package com.example.kittens.data

import com.example.kittens.data.database.models.Breed
import com.example.kittens.data.database.models.CatBreedCrossRef
import com.example.kittens.data.database.models.CatWithBreeds
import com.example.kittens.domain.models.Cat as DomainCat
import com.example.kittens.domain.models.Breed as DomainBreed
import com.example.kittens.data.network.models.Cat as NetworkCat
import com.example.kittens.data.database.models.Cat as DatabaseCat

class CatsMapper {
    data class CatDatabaseBundle(
        val cats: List<DatabaseCat>,
        val breeds: List<Breed>,
        val crossRefs: List<CatBreedCrossRef>
    )

    fun mapNetworkToDatabase(networkCats: List<NetworkCat>): CatDatabaseBundle {
        val cats = mutableListOf<DatabaseCat>()
        val allBreeds = mutableMapOf<String, Breed>()
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

            if (networkCat.breeds != null) {
                for (networkBreed in networkCat.breeds) {
                    // Додаємо лише унікальні породи
                    allBreeds[networkBreed.id] = Breed(
                        id = networkBreed.id,
                        name = networkBreed.name,
                        temperament = networkBreed.temperament,
                        origin = networkBreed.origin,
                        countryCodes = networkBreed.countryCodes,
                        countryCode = networkBreed.countryCode,
                        lifeSpan = networkBreed.lifeSpan,
                        wikipediaUrl = networkBreed.wikipediaUrl
                    )

                    // Зв’язок кіт-порода
                    crossRefs.add(
                        CatBreedCrossRef(
                            catId = networkCat.id,
                            breedId = networkBreed.id
                        )
                    )
                }
            }
        }

        return CatDatabaseBundle(
            cats = cats,
            breeds = allBreeds.values.toList(),
            crossRefs = crossRefs
        )
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

    fun mapCatsWithBreedsToDomain(catsWithBreeds: List<CatWithBreeds>): List<DomainCat> {
        return catsWithBreeds.map { catWithBreeds ->
            DomainCat(
                id = catWithBreeds.cat.id,
                url = catWithBreeds.cat.url,
                width = catWithBreeds.cat.width,
                height = catWithBreeds.cat.height,
                breeds = catWithBreeds.breeds.map { breed ->
                    DomainBreed(
                        id = breed.id,
                        name = breed.name,
                        temperament = breed.temperament,
                        origin = breed.origin,
                        //countryCodes = breed.countryCodes,
                        //countryCode = breed.countryCode,
                        //lifeSpan = breed.lifeSpan,
                        //wikipediaUrl = breed.wikipediaUrl
                    )
                }
            )
        }
    }
}