package com.example.kittens.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kittens.core.App
import com.example.kittens.data.BreedsRepo
import com.example.kittens.domain.models.Breed
import kotlinx.coroutines.launch

class BreedsViewModel : ViewModel() {
    private val repo = App.catService?.let { BreedsRepo(it) }
    val breeds = MutableLiveData<List<Breed>>()
    private var breedsList: List<Breed>? = null

    fun obtainBreeds() {
//        val call: Call<MutableList<Breed>>? = App.catService?.getAllBreeds()
//        call?.enqueue(object : Callback<MutableList<Breed>> {
//            override fun onResponse(
//                call: Call<MutableList<Breed>>,
//                response: Response<MutableList<Breed>>
//            ) {
//                val breedsList = response.body()
//                this@BreedsViewModel.breedsList = breedsList
//
//            }
//
//            override fun onFailure(call: Call<MutableList<Breed>>, t: Throwable) {
//                t.message?.let {
//                    Log.e("BreedsFragment", it)
//                }
//            }
//        })

        viewModelScope.launch {
            breedsList = repo?.obtainBreeds()
            breedsList?.let { breeds.postValue(it) }
        }
    }
}