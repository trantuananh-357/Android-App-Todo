package com.example.android_todoapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android_todoapp.model.TaskModel

@Entity(
    tableName = "task_table",
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Long = 0,
    val taskName: String,
    val category: String,
    val dateTime: String,
    val startTime: String,
    val endTime: String,
    val status : String,
    val description: String = "",
) {
    fun toTaskModel() = TaskModel(
        id = taskId,
        taskName = taskName,
        category = category,
        dateTime = dateTime,
        startTime = startTime,
        endTime = endTime,
        status = status,
        description = description
    )
}