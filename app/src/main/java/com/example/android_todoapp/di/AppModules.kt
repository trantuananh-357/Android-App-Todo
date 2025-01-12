package com.example.android_todoapp.di

import org.koin.core.module.Module

val appModules : List<Lazy<Module>> = buildList {
    add(applicationScope)
    add(viewModelModule)
    add(roomModule)
    add(repositoryModules)
    add(notificationModule)
}