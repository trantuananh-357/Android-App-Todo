package com.example.android_todoapp.data.repository

import com.example.android_todoapp.data.room.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface ITaskRepo {
    suspend fun createTask(taskEntity: TaskEntity)
    fun getListTasks(userId: Int, taskId: Int): Flow<List<TaskEntity>>
    fun getAllTasksByUserId(userId: Int): Flow<List<TaskEntity>>
    suspend fun updateTask(taskEntity: TaskEntity)
    suspend fun deleteTask(toDoId: Int)
    fun getCountTaskInCategoryByTag(tag: String): Flow<Int>
    fun getTaskByTag(tag: String): Flow<List<TaskEntity>>
}