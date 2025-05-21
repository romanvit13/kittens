package com.example.kittens.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kittens.data.repos.CatsRepo
import com.example.kittens.data.providers.ImageProvider
import com.example.kittens.data.repos.FavouriteCatsRepo
import com.example.kittens.domain.models.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatsViewModel(
    private val repo: CatsRepo,
    private val favouriteCatsRepo: FavouriteCatsRepo,
    private val imageProvider: ImageProvider? = null
) : ViewModel() {
    private val _cats = MutableStateFlow<List<Cat>>(emptyList())
    val cats: StateFlow<List<Cat>> = _cats // Data source for compose functions

    private val _favouriteStatus = MutableStateFlow<UiStatus>(UiStatus.Idle)
    val favouriteStatus: StateFlow<UiStatus> = _favouriteStatus

    private val _catsLive = MutableLiveData<List<Cat>>(emptyList())
    val catsLive: LiveData<List<Cat>> = _catsLive // Data source for fragments

    val isLoading = MutableStateFlow(true)

    init {
        obtainCats()
    }

    private fun obtainCatsOld() {
        isLoading.value = true
        viewModelScope.launch {
            val catList = repo.obtainCats()
            _cats.value = catList
            _catsLive.value = catList
            isLoading.value = false
        }
    }

    private fun obtainCats() {
        isLoading.value = true
        viewModelScope.launch {
            val allCats = repo.obtainCats()

            _cats.value = allCats
            _catsLive.value = allCats
            isLoading.value = false
        }
    }

    fun addFavouriteCat(imageId: String, subId: String?) {
        viewModelScope.launch {
            _favouriteStatus.value = UiStatus.Loading
            val result = favouriteCatsRepo.addFavouriteCat(imageId, subId)
            _favouriteStatus.value = result.fold(
                onSuccess = { UiStatus.Success("Added to favourites") },
                onFailure = { UiStatus.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun toggleFavourite(cat: Cat) {
        viewModelScope.launch {
            _favouriteStatus.value = UiStatus.Loading
            val result = if (cat.isFavourite()) {
                favouriteCatsRepo.removeFavouriteCat(cat.favId!!)
                    .fold(
                        onSuccess = { UiStatus.Success("Removed from favourites") },
                        onFailure = { UiStatus.Error(it.message ?: "Unknown error") }
                    )
            } else {
                favouriteCatsRepo.addFavouriteCat(cat.id, null)
                    .fold(
                        onSuccess = { UiStatus.Success("Added to favourites") },
                        onFailure = { UiStatus.Error(it.message ?: "Unknown error") }
                    )
            }
            _favouriteStatus.value = result
            // update UI with new fav state
            val updatedList = _cats.value.map {
                if (it.id == cat.id) it.copy(favId = cat.favId) else it
            }
            _cats.value = updatedList
        }
    }
}
