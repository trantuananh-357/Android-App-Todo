package com.example.android_todoapp.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_todoapp.data.room.dao.TaskDao
import com.example.android_todoapp.data.room.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 3, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract val toDoDao: TaskDao
}
