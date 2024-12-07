package com.example.android_todoapp.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.android_todoapp.model.TaskScheduler
import com.example.android_todoapp.notification.NotificationService
import org.koin.java.KoinJavaComponent.inject

class TaskAlarmReceiver : BroadcastReceiver() {
    private val notificationService: NotificationService by inject(NotificationService::class.java)
    private val taskScheduler: TaskScheduler by inject(TaskScheduler::class.java)

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_TIME_CHANGED,
            Intent.ACTION_TIMEZONE_CHANGED -> {
                taskScheduler.rescheduleAllTasks()
            }

            else -> handleTaskNotification(intent)
        }
    }

    private fun handleTaskNotification(intent: Intent) {
        val taskId = intent.getLongExtra("taskId", -1)
        val taskName = intent.getStringExtra("taskName") ?: return
        val isStartNotification = intent.getBooleanExtra("isStartNotification", true)

        if (isStartNotification) {
            notificationService.showTaskStartNotification(taskId, taskName)
        } else {
            notificationService.showTaskEndingSoonNotification(taskId, taskName)
        }
    }
}