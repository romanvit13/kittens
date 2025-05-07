package com.example.kittens.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kittens.data.CatsRepo
import com.example.kittens.domain.models.Cat
import kotlinx.coroutines.launch

class CatsViewModel(private val repo: CatsRepo) : ViewModel() {
    val cats = MutableLiveData<List<Cat>>()
    private var catsList: List<Cat>? = null

    init {
        obtainCats()
    }

    fun obtainCats() {
        viewModelScope.launch {
            catsList = repo.obtainCats()
            catsList?.let { cats.postValue(it) }
        }
    }
}