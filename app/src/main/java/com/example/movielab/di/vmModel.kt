package com.example.movielab.di

import com.example.movielab.viewModels.MovieViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val vmModule = module {
    viewModel { MovieViewModel(get()) }
}