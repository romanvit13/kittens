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

    private var currentPage = 0
    private var isLastPage = false

    val isLoadingMore = MutableStateFlow(false)

    init {
        obtainCats()
    }

    fun obtainCats() {
        isLoading.value = true
        viewModelScope.launch {
            val allCats = repo.obtainCats()

            _cats.value = allCats
            _catsLive.value = allCats
            isLoading.value = false
        }
    }

    fun loadMoreCats() {
        if (isLoadingMore.value || isLastPage) return

        isLoadingMore.value = true
        viewModelScope.launch {
            val newCats = repo.obtainCats()

            if (newCats.isEmpty()) {
                isLastPage = true
            } else {
                _cats.value = _cats.value + newCats
                _catsLive.value = _cats.value
            }

            isLoadingMore.value = false
            currentPage++
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

            val result: Result<Long?> = if (cat.isFavourite()) {
                cat.favId?.let {
                    favouriteCatsRepo.removeFavouriteCat(it)
                } ?: Result.failure(Exception("Missing favId"))
            } else {
                favouriteCatsRepo.addFavouriteCat(cat.id)
            }

            result.fold(
                onSuccess = { favId ->
                    _cats.value = _cats.value.map { currentCat ->
                        if (currentCat.id == cat.id) {
                            currentCat.copy(favId = favId)
                        } else currentCat
                    }
                    _favouriteStatus.value = UiStatus.Success("Updated successfully")
                },
                onFailure = { error ->
                    _favouriteStatus.value = UiStatus.Error(error.message ?: "Unknown error")
                }
            )
        }
    }
}
