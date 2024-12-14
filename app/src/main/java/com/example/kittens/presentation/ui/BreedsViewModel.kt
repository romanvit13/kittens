package com.example.kittens.presentation.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kittens.core.App
import com.example.kittens.data.network.models.Breed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BreedsViewModel : ViewModel() {
    val breeds = MutableLiveData<List<Breed>>()
    private var breedsList: List<Breed>? = null

    fun obtainBreeds() {
        val call: Call<MutableList<Breed>>? = App.catService?.getAllBreeds()
        call?.enqueue(object : Callback<MutableList<Breed>> {
            override fun onResponse(
                call: Call<MutableList<Breed>>,
                response: Response<MutableList<Breed>>
            ) {
                val breedsList = response.body()
                this@BreedsViewModel.breedsList = breedsList
                breedsList?.let { breeds.postValue(it) }
            }

            override fun onFailure(call: Call<MutableList<Breed>>, t: Throwable) {
                t.message?.let {
                    Log.e("BreedsFragment", it)
                }
            }
        })
    }

    fun loadBreedsFromDb() {
        var breeds: List<com.example.kittens.data.database.models.Breed>? = null
        val runnable = {
            breeds = App.appDatabase?.breedDao()?.getAll()
        }
        Thread(runnable).start()
    }
}