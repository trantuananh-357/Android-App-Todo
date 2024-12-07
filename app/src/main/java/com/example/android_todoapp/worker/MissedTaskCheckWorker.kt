package com.example.android_todoapp.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.android_todoapp.notification.NotificationService
import org.koin.java.KoinJavaComponent.inject

class MissedTaskCheckWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    private val notificationService: NotificationService by inject(NotificationService::class.java)

    override fun doWork(): Result {
        try {
            val taskId = inputData.getLong("taskId", -1)
            val taskName = inputData.getString("taskName") ?: return Result.failure()

            if (taskId != -1L) {
                notificationService.showMissedTaskNotification(taskId, taskName)
                return Result.success()
            }

            return Result.failure()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}