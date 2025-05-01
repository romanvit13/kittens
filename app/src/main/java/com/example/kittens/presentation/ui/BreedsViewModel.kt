package com.example.kittens.presentation.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kittens.common.NetworkUtils
import com.example.kittens.core.App
import com.example.kittens.data.BreedMapper
import com.example.kittens.data.BreedsRepo
import com.example.kittens.domain.models.Breed
import kotlinx.coroutines.launch

class BreedsViewModel(private val context: Application) : AndroidViewModel(context) {
    private val breedService = App.catService
    private val breedDao = App.appDatabase?.breedDao()

    val breeds = MutableLiveData<List<Breed>>()
    private var breedsList: List<Breed>? = null

    init {
        obtainBreeds()
    }

    fun obtainBreeds() {
        viewModelScope.launch {
            if (breedDao != null && breedService != null) {
                val repo = BreedsRepo(breedService, breedDao, BreedMapper(), NetworkUtils(context))
                breedsList = repo.obtainBreeds()
                breedsList?.let { breeds.postValue(it) }
            }
        }
    }
}