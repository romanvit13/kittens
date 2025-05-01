package com.example.kittens.presentation.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kittens.core.App
import androidx.lifecycle.viewModelScope
import com.example.kittens.common.NetworkUtils
import com.example.kittens.data.CatsMapper
import com.example.kittens.data.CatsRepo
import com.example.kittens.domain.models.Cat
import kotlinx.coroutines.launch

class CatsViewModel(private val context: Application) : AndroidViewModel(context) {
    private val catService = App.catService
    private val catDao = App.appDatabase?.catDao()

    val cats = MutableLiveData<List<Cat>>()
    private var catsList: List<Cat>? = null

    init {
        obtainCats()
    }

    fun obtainCats() {
        viewModelScope.launch {
            if (catDao != null && catService != null) {
                val repo = CatsRepo(catService, catDao, CatsMapper(), NetworkUtils(context))
                catsList = repo.obtainCats()
                catsList?.let { cats.postValue(it) }
            }
        }
    }
}