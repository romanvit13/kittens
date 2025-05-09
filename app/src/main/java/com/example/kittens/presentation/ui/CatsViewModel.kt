package com.example.kittens.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kittens.data.CatsRepo
import com.example.kittens.data.ImageProvider
import com.example.kittens.domain.models.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatsViewModel(
    private val repo: CatsRepo,
    private val imageProvider: ImageProvider? = null
) : ViewModel() {
    private val _cats = MutableStateFlow<List<Cat>>(emptyList())
    val cats: StateFlow<List<Cat>> = _cats


    init {
        obtainCats()
    }

    private fun obtainCats() {
        viewModelScope.launch {
            val catList = repo.obtainCats()
            _cats.value = catList
        }
    }
}
