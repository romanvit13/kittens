package com.example.kittens.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kittens.databinding.FragmentBreedsBinding

class BreedsFragment : Fragment() {
    private var _binding: FragmentBreedsBinding? = null
    private val binding get() = _binding!!
    private var viewModel: BreedsViewModel? = null

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

        viewModel = ViewModelProvider(this)[BreedsViewModel::class.java]

        binding.saveToDbBtn.setOnClickListener {
            // to do
        }

        binding.loadFromDb.setOnClickListener {
            viewModel?.loadBreedsFromDb()
        }

        viewModel?.obtainBreeds()

        viewModel?.breeds?.observe(viewLifecycleOwner) {
            binding.breedsInfoTv.text = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}