package com.example.android_todoapp.model

interface TaskScheduler {
    fun scheduleTask(task: TaskModel)
    fun cancelTask(taskId: Long)
    fun rescheduleAllTasks()
}