package com.example.kittens.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kittens.App
import com.example.kittens.databinding.FragmentBreedsBinding
import com.example.kittens.models.Breed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BreedsFragment: Fragment() {

    private var _binding: FragmentBreedsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBreeds()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getBreeds() {
        val call: Call<MutableList<Breed>>? = App.catService?.getAllBreeds()
        call?.enqueue(object : Callback<MutableList<Breed>> {
            override fun onResponse(
                call: Call<MutableList<Breed>>,
                response: Response<MutableList<Breed>>
            ) {
                val breedsList = response.body()
                val breedsString = StringBuilder()
                if (breedsList != null) {
                    for (breed in breedsList) {
                        Log.d("BreedsFragment", "Breed: $breed")
                        breedsString.append(breed).append("\n")
                    }
                }

                binding.breedsInfoTv.text = breedsString.toString()
            }

            override fun onFailure(call: Call<MutableList<Breed>>, t: Throwable) {
                t.message?.let {
                    Log.e("BreedsFragment", it)
                }
            }
        })
    }
}