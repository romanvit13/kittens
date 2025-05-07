package com.example.kittens.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kittens.data.BreedsRepo
import com.example.kittens.domain.models.Breed
import kotlinx.coroutines.launch

class BreedsViewModel(
    private val breedsRepo: BreedsRepo,
) : ViewModel() {
    val breeds = MutableLiveData<List<Breed>>()
    private var breedsList: List<Breed>? = null

    init {
        obtainBreeds()
    }

    private fun obtainBreeds() {
        viewModelScope.launch {
            breedsList = breedsRepo.obtainBreeds()
            breedsList?.let { breeds.postValue(it) }
        }
    }
}