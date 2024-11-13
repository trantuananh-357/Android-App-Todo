package com.example.android_todoapp.data.repository.impl

import com.example.android_todoapp.data.repository.TaskRepo
import com.example.android_todoapp.data.room.dao.TaskDao
import com.example.android_todoapp.data.room.entity.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TaskRepoImpl(private val taskDao: TaskDao) : TaskRepo {

    override suspend fun createTask(taskEntity: TaskEntity) = withContext(Dispatchers.IO) {
        taskDao.createTask(taskEntity)
    }

    override suspend fun getListTasks(userId: Int, taskId: Int): Flow<List<TaskEntity>> {
        return taskDao.getListTasks(userId, taskId).flowOn(Dispatchers.IO)
    }

    override suspend fun updateTask(taskEntity: TaskEntity, toDoId: Int) =
        withContext(Dispatchers.IO) {
            taskDao.updateTask(taskEntity, toDoId)
        }

    override suspend fun deleteTask(toDoId: Int) = withContext(Dispatchers.IO) {
        taskDao.deleteTask(toDoId)
    }
}