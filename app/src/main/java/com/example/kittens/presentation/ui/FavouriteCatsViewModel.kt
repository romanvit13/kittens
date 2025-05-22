package com.example.kittens.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kittens.data.repos.FavouriteCatsRepo
import com.example.kittens.domain.models.FavouriteCat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouriteCatsViewModel(
    private val favouriteCatsRepo: FavouriteCatsRepo,
) : ViewModel() {
    private val TAG = "FavouriteCatsViewModel"
    private val _favCats = MutableStateFlow<List<FavouriteCat>>(emptyList())
    val favCats: StateFlow<List<FavouriteCat>> = _favCats // Data source for compose functions

    val isLoading = MutableStateFlow(true)

    init {
        obtainFavCats()
    }

    private fun obtainFavCats() {
        isLoading.value = true
        viewModelScope.launch {
            val result = favouriteCatsRepo.getFavouriteCats()
            if (result.isSuccess) {
                val favCatsList = result.getOrNull()
                if (favCatsList != null) {
                    _favCats.value = favCatsList
                }
            } else {
                Log.e(TAG, "Couldn't fetch favourite cats data")
            }
            isLoading.value = false
        }
    }

    fun removeFavouriteCat(favouriteCat: FavouriteCat) {
        viewModelScope.launch {
            val result = favouriteCatsRepo.removeFavouriteCat(favouriteCat.id)
            if (result.isSuccess) {
                val favCatsMutable = _favCats.value.toMutableList()
                favCatsMutable.remove(favouriteCat)
                _favCats.value = favCatsMutable
            } else {
                Log.e(TAG, "Couldn't remove favourite cat with id: ${favouriteCat.id}")
            }
        }
    }
}