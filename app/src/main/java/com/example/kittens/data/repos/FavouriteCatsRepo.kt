package com.example.kittens.data.repos

import com.example.kittens.data.mappings.FavouriteCatsMapper
import com.example.kittens.data.network.IFavouriteCatService
import com.example.kittens.data.network.models.FavouriteCatRequest
import com.example.kittens.domain.IFavouriteCatsRepo
import com.example.kittens.domain.models.FavouriteCat

class FavouriteCatsRepo(
    private val api: IFavouriteCatService,
    private val mapper: FavouriteCatsMapper,
) : IFavouriteCatsRepo {

    override suspend fun addFavouriteCat(imageId: String, subId: String?): Result<Long?> {
        return try {
            val request = FavouriteCatRequest(imageId, subId)
            val response = api.addFavouriteCat(request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.id)
                } else {
                    Result.failure(Exception("Empty response body when adding a favourite cat"))
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Unknown error from server"
                Result.failure(Exception("API error: $errorMsg"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFavouriteCat(favouriteId: Long): Result<Long?> {
        return try {
            val response = api.removeFavouriteCat(favouriteId)
            if (response.isSuccessful) {
                Result.success(response.body()?.id)
            } else {
                val errorText = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception(errorText))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFavouriteCats(): Result<List<FavouriteCat>> {
        return try {
            val response = api.getFavouriteCats()
            val domainCats = response.map { networkCats ->
                mapper.mapNetworkToDomain(networkCats)
            }
            Result.success(domainCats)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}