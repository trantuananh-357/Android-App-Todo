package com.example.android_todoapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android_todoapp.data.room.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun createTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Query("DELETE FROM task_table WHERE taskId = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("SELECT COUNT(*) FROM task_table WHERE category = :tag")
    fun getCountTaskInCategoryByTag(tag: String): Flow<Int>

    @Query("SELECT * FROM task_table WHERE category = :tag")
    fun getTasksByTag(tag: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE taskId = :id")
    fun getTasksById(id: Long): Flow<TaskEntity>
}