package com.example.android_todoapp.di

import org.koin.core.module.Module

val appModules : List<Lazy<Module>> = buildList {
    add(applicationScope)
    add(viewModelModule)
}