package com.example.android_todoapp.notification

interface NotificationService {
    fun showTaskStartNotification(taskId: Long, taskName: String)
    fun showTaskEndingSoonNotification(taskId: Long, taskName: String)
    fun showMissedTaskNotification(taskId: Long, taskName: String)
    fun createNotificationChannel()
}