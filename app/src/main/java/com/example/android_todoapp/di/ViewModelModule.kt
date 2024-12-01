package com.example.android_todoapp.di

import com.example.android_todoapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.lazyModule

val viewModelModule = lazyModule {
    viewModel {
        HomeViewModel(get(), get())
    }
}