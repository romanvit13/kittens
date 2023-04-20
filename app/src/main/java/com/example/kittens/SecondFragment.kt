package com.example.kittens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kittens.databinding.FragmentSecondBinding
import com.example.kittens.models.Cat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        getCats()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCats() {
        val call: Call<List<Cat>>? = App.catService?.getCatsListLimit20()
        Log.d("FirstFragment", "getCats(), call: $call")
        call?.enqueue(object : Callback<List<Cat>> {
            override fun onResponse(
                call: Call<List<Cat>>,
                response: Response<List<Cat>>
            ) {
                Log.d("FirstFragment", "onResponse() callback")
                val resultCats: List<Cat>? = response.body()
                if (resultCats != null) {
                    for (resultCat in resultCats) {
                        Log.d("FirstFragment","Result cat: $resultCat")
                    }
                }
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                Log.i("Cats", "Something went wrong.")
            }
        })
    }
}