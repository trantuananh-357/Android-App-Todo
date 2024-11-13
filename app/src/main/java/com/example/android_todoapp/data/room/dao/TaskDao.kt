package com.example.android_todoapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android_todoapp.data.room.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun createTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM task_table WHERE userId = :userId AND taskId = :taskId")
    suspend fun getListTasks(userId: Int, taskId: Int): Flow<List<TaskEntity>>

    @Query("DELETE FROM task_table WHERE taskId = :taskId")
    suspend fun updateTask(taskEntity: TaskEntity, taskId: Int)

    @Query("DELETE FROM task_table WHERE taskId = :taskId")
    suspend fun deleteTask(taskId: Int)

}