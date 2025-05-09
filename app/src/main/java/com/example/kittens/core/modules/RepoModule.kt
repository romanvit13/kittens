package com.example.kittens.core.modules

import com.example.kittens.data.BreedMapper
import com.example.kittens.data.BreedsRepo
import com.example.kittens.data.CatsMapper
import com.example.kittens.data.CatsRepo
import com.example.kittens.data.GlideImageProvider
import com.example.kittens.data.ImageProvider
import org.koin.dsl.module

val repositoryModule = module {
    single { CatsMapper() }
    single { BreedMapper() }
    single { CatsRepo(get(), get(), get(), get()) }
    single { BreedsRepo(get(), get(), get(), get()) }
    single<ImageProvider> { GlideImageProvider(get()) }
}