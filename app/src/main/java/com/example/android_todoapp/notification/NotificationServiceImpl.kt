package com.example.android_todoapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.android_todoapp.R
import com.example.android_todoapp.ui.edit.EditActivity

class NotificationServiceImpl(private val context: Context) : NotificationService {
    companion object {
        const val CHANNEL_ID = "task_notifications"
        private const val START_NOTIFICATION_ID = 1
        private const val END_NOTIFICATION_ID = 2
        private const val MISSED_NOTIFICATION_ID = 3
    }

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    override fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Task Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for task management"
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun showTaskStartNotification(taskId: Long, taskName: String) {
        showNotification(
            taskId,
            "Task Starting",
            "Time to start: $taskName",
            START_NOTIFICATION_ID
        )
    }

    override fun showTaskEndingSoonNotification(taskId: Long, taskName: String) {
        showNotification(
            taskId,
            "Task Ending Soon",
            "$taskName will end in 10 minutes",
            END_NOTIFICATION_ID
        )
    }

    override fun showMissedTaskNotification(taskId: Long, taskName: String) {
        showNotification(
            taskId,
            "Missed Task",
            "You missed the task: $taskName",
            MISSED_NOTIFICATION_ID
        )
    }

    private fun showNotification(
        taskId: Long,
        title: String,
        content: String,
        notificationId: Int
    ) {
        val intent = Intent(context, EditActivity::class.java).apply {
            putExtra(EditActivity.ID_TASK_KEY, taskId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            taskId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_date)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationId + taskId.toInt(), notification)
    }
}