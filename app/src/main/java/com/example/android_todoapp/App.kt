package com.example.android_todoapp

import android.app.Application
import com.example.android_todoapp.di.appModules
import com.example.android_todoapp.notification.NotificationService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.lazyModules
import org.koin.core.runOnKoinStarted
import org.koin.core.waitAllStartJobs
import org.koin.mp.KoinPlatform

class App : Application() {
    companion object {
        private lateinit var instance: App
        fun getInstance(): App = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger()
            androidContext(this@App)
            lazyModules(appModules)
        }

        KoinPlatform.getKoin().apply {
            waitAllStartJobs()
            runOnKoinStarted { koin ->
                koin.get<NotificationService>().createNotificationChannel()
            }
        }
    }

}