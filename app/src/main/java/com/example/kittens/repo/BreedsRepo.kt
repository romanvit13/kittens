package com.example.kittens.repo

import android.util.Log
import com.example.kittens.App
import com.example.kittens.models.Breed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BreedsRepo {
    private var breedsList: List<Breed>? = null

    fun obtainBreeds() {
        val call: Call<MutableList<Breed>>? = App.catService?.getAllBreeds()
        call?.enqueue(object : Callback<MutableList<Breed>> {
            override fun onResponse(
                call: Call<MutableList<Breed>>,
                response: Response<MutableList<Breed>>
            ) {
                val breedsList = response.body()
                this@BreedsRepo.breedsList = breedsList
                breedsList?.let {  }
            }

            override fun onFailure(call: Call<MutableList<Breed>>, t: Throwable) {
                t.message?.let {
                    Log.e("BreedsFragment", it)
                }
            }
        })
    }

    fun loadBreedsFromDb() {
        var breeds: List<com.example.kittens.room.models.Breed>? = null
        val runnable = {
            breeds = App.appDatabase?.breedDao()?.getAll()
        }
        Thread(runnable).start()
    }
}