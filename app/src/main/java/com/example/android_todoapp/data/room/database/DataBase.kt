package com.example.android_todoapp.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_todoapp.data.room.dao.TaskDao
import com.example.android_todoapp.data.room.dao.UserDao
import com.example.android_todoapp.data.room.entity.TaskEntity
import com.example.android_todoapp.data.room.entity.UserEntity

@Database(entities = [UserEntity::class, TaskEntity::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val toDoDao: TaskDao
}
