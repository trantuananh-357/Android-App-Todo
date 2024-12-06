package com.example.android_todoapp.data.repository.impl

import com.example.android_todoapp.data.repository.ITaskRepo
import com.example.android_todoapp.data.room.dao.TaskDao
import com.example.android_todoapp.data.room.entity.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TaskRepoImpl(private val taskDao: TaskDao) : ITaskRepo {

    override suspend fun createTask(taskEntity: TaskEntity) = withContext(Dispatchers.IO) {
        taskDao.createTask(taskEntity)
    }

    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAllTasks().flowOn(Dispatchers.IO)
    }

    override suspend fun updateTask(taskEntity: TaskEntity) =
        withContext(Dispatchers.IO) {
            taskDao.updateTask(taskEntity)
        }

    override suspend fun deleteTask(toDoId: Int) = withContext(Dispatchers.IO) {
        taskDao.deleteTask(toDoId)
    }

    override fun getCountTaskInCategoryByTag(tag: String): Flow<Int> {
        return taskDao.getCountTaskInCategoryByTag(tag)
    }

    override fun getTaskByTag(tag: String): Flow<List<TaskEntity>> {
        return taskDao.getTasksByTag(tag)
    }

    override fun getTaskById(id: Long): Flow<TaskEntity> {
        return taskDao.getTasksById(id)
    }
}