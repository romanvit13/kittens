package com.example.kittens.core.modules

import com.example.kittens.presentation.ui.BreedsViewModel
import com.example.kittens.presentation.ui.CatsViewModel
import com.example.kittens.presentation.ui.FavouriteCatsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // ViewModel
    viewModel { CatsViewModel(get(), get()) }
    viewModel { BreedsViewModel(get()) }
    viewModel { FavouriteCatsViewModel(get()) }
}