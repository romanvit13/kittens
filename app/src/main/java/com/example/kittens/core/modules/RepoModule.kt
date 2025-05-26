package com.example.kittens.core.modules

import com.example.kittens.data.mappings.BreedMapper
import com.example.kittens.data.repos.BreedsRepo
import com.example.kittens.data.mappings.CatsMapper
import com.example.kittens.data.mappings.FavouriteCatsMapper
import com.example.kittens.data.repos.CatsRepo
import com.example.kittens.data.providers.GlideImageProvider
import com.example.kittens.data.providers.ImageProvider
import com.example.kittens.data.repos.FavouriteCatsRepo
import org.koin.dsl.module

val repositoryModule = module {
    single { BreedMapper() }
    single { CatsMapper(get()) }
    single { CatsRepo(get(), get(), get(), get(), get()) }
    single { BreedsRepo(get(), get(), get(), get()) }
    single { FavouriteCatsMapper() }
    single { FavouriteCatsRepo(get(), get(), get(), get()) }
    single<ImageProvider> { GlideImageProvider(get()) }
}