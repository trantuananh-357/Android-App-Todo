package com.example.android_todoapp.di

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.qualifier.named
import org.koin.dsl.lazyModule

@OptIn(KoinExperimentalAPI::class)
val applicationScope = lazyModule {
    single(named("coroutine_app")) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, _ ->  })
    }
}
