package com.example.android_todoapp.data.repository

import com.example.android_todoapp.data.room.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepo {
    suspend fun createTask(taskEntity: TaskEntity)
    suspend fun getListTasks(userId: Int, taskId: Int): Flow<List<TaskEntity>>
    suspend fun updateTask(taskEntity: TaskEntity, toDoId: Int)
    suspend fun deleteTask(toDoId: Int)

}