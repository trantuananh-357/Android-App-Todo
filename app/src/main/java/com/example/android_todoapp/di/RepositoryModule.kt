package com.example.android_todoapp.di

import com.apero.common.data.pref.SharePref
import com.example.android_todoapp.data.repository.IDataRepo
import com.example.android_todoapp.data.repository.ITaskRepo
import com.example.android_todoapp.data.repository.LanguageRepo
import com.example.android_todoapp.data.repository.impl.DataRepoImpl
import com.example.android_todoapp.data.repository.impl.LanguageRepoImpl
import com.example.android_todoapp.data.repository.impl.TaskRepoImpl
import com.example.android_todoapp.model.TaskScheduler
import com.example.android_todoapp.notification.NotificationService
import com.example.android_todoapp.notification.NotificationServiceImpl
import com.example.android_todoapp.scheduler.AlarmSchedulingStrategy
import com.example.android_todoapp.scheduler.TaskSchedulerImpl
import com.example.android_todoapp.scheduler.WorkSchedulingStrategy
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.lazyModule

val repositoryModules = lazyModule {
    single { SharePref(context = androidApplication()) }
    single { DataRepoImpl(get()) } bind IDataRepo::class
    single { TaskRepoImpl(get()) } bind ITaskRepo::class
    single { LanguageRepoImpl(get()) } bind LanguageRepo::class
}

val notificationModule = lazyModule {
    singleOf(::NotificationServiceImpl) bind NotificationService::class
    singleOf(::AlarmSchedulingStrategy)
    singleOf(::WorkSchedulingStrategy)

    single {
        TaskSchedulerImpl(
            alarmStrategy = get<AlarmSchedulingStrategy>(),
            workStrategy = get<WorkSchedulingStrategy>()
        )
    } bind TaskScheduler::class
}




