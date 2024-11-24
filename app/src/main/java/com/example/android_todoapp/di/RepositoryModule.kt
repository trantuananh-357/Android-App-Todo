package com.example.android_todoapp.di

import com.apero.common.data.pref.SharePref
import com.example.android_todoapp.data.repository.IDataRepo
import com.example.android_todoapp.data.repository.ITaskRepo
import com.example.android_todoapp.data.repository.IUserRepo
import com.example.android_todoapp.data.repository.impl.DataRepoImpl
import com.example.android_todoapp.data.repository.impl.TaskRepoImpl
import com.example.android_todoapp.data.repository.impl.UserRepoImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.lazyModule

val repositoryModules = lazyModule {
    single { SharePref(context = androidApplication()) }
    single { DataRepoImpl(get(), get()) } bind IDataRepo::class
    single { TaskRepoImpl(get()) } bind ITaskRepo::class
    single { UserRepoImpl(get()) } bind IUserRepo::class
}






