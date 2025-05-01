package com.example.kittens.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kittens.core.App
import com.example.kittens.R
import com.example.kittens.databinding.FragmentSecondBinding
import com.example.kittens.data.network.models.Cat
import com.example.kittens.presentation.recycler.KittensAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private lateinit var viewModel: CatsViewModel
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CatsViewModel::class.java]
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
        }

        viewModel.obtainCats()

        viewModel.cats.observe(viewLifecycleOwner) {
            val recyclerView: RecyclerView = binding.kittensRv
            val recyclerViewAdapter = KittensAdapter(it.toMutableList())
            recyclerView.adapter = recyclerViewAdapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCatsOld() {
        val call: Call<MutableList<Cat>>? = App.catService?.getCatsListLimit20()
        Log.d("FirstFragment", "getCats(), call: $call")
        call?.enqueue(object : Callback<MutableList<Cat>> {
            override fun onResponse(
                call: Call<MutableList<Cat>>,
                response: Response<MutableList<Cat>>
            ) {
                Log.d("FirstFragment", "onResponse() callback")
                val resultCats: MutableList<Cat>? = response.body()
                if (resultCats != null) {
                    for (resultCat in resultCats) {
                        Log.d("FirstFragment","Result cat: $resultCat")
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<Cat>>, t: Throwable) {
                t.message?.let { Log.e("FirstFragment", "Message: $it, cause: ${t.cause?.message}") }
            }
        })
    }
}