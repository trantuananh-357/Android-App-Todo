package com.example.android_todoapp.model

import androidx.annotation.StringDef
import com.example.android_todoapp.data.room.entity.TaskEntity

data class TaskModel(
    val id: Long,
    val taskName: String,
    val category: String,
    val dateTime: String,
    val startTime: String,
    @StateStatusTask val status: String,
    val endTime: String,
    val description: String = "",
) {
    fun toTaskEntity(userId : Int) = TaskEntity(
        taskId = id,
        taskName = taskName,
        category = category,
        dateTime = dateTime,
        startTime = startTime,
        status = status,
        endTime = endTime,
        description = description,
        userId = userId.toLong()
    )
}

const val DONE = "DONE"
const val TODO = "TODO"

@StringDef(
    value = [
        DONE,
        TODO,
    ]
)
@Retention(AnnotationRetention.SOURCE)
annotation class StateStatusTask
