package com.example.android_todoapp.model

interface TaskSchedulingStrategy {
    fun schedule(task: TaskModel)
    fun cancel(taskId: Long)
}