package com.example.android_todoapp.di

import androidx.room.Room
import com.example.android_todoapp.App
import com.example.android_todoapp.data.room.database.DataBase
import org.koin.dsl.lazyModule

val roomModule = lazyModule {
    single {
        Room.databaseBuilder(
            App.getInstance(),
            DataBase::class.java,
            "todo_database"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<DataBase>().toDoDao }
}