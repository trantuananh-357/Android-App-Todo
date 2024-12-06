package com.example.android_todoapp.model

import androidx.annotation.StringDef
import com.example.android_todoapp.data.room.entity.TaskEntity

data class TaskModel(
    val id: Long = 0L,
    val taskName: String = "",
    val category: String = WORK,
    val dateTime: String = "",
    val startTime: String = "",
    @StateStatusTask val status: String = TODO,
    val endTime: String = "",
    val description: String = "",
) {
    fun toTaskEntity() = TaskEntity(
        taskId = id,
        taskName = taskName,
        category = category,
        dateTime = dateTime,
        startTime = startTime,
        status = status,
        endTime = endTime,
        description = description,
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
