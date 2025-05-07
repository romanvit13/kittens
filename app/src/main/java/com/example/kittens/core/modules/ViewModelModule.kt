package com.example.kittens.core.modules

import com.example.kittens.presentation.ui.BreedsViewModel
import com.example.kittens.presentation.ui.CatsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // ViewModel
    viewModel { CatsViewModel(get()) }
    viewModel { BreedsViewModel(get()) }
}