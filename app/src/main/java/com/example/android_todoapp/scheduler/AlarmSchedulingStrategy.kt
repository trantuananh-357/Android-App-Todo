package com.example.android_todoapp.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.android_todoapp.model.TaskModel
import com.example.android_todoapp.model.TaskSchedulingStrategy
import com.example.android_todoapp.worker.TaskAlarmReceiver
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmSchedulingStrategy(private val context: Context) : TaskSchedulingStrategy {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private val pendingIntents = mutableMapOf<String, PendingIntent>()

    override fun schedule(task: TaskModel) {
        cancel(task.id)
        scheduleStartAlarm(task)
        scheduleEndAlarm(task)
    }

    fun scheduleStartAlarm(task: TaskModel) {
        val startTime = calculateStartTime(task)
        if (startTime > System.currentTimeMillis()) {
            scheduleAlarm(task.id, task.taskName, startTime, true)
        }
    }

    fun scheduleEndAlarm(task: TaskModel) {
        val endTime = calculateEndTime(task)
        if (endTime > System.currentTimeMillis()) {
            val notificationTime = endTime - TimeUnit.MINUTES.toMillis(10)
            if (notificationTime > System.currentTimeMillis()) {
                scheduleAlarm(task.id, task.taskName, notificationTime, false)
            }
        }
    }

    override fun cancel(taskId: Long) {
        val startKey = getIntentKey(taskId, true)
        pendingIntents[startKey]?.let {
            alarmManager.cancel(it)
            it.cancel()
        }
        pendingIntents.remove(startKey)

        // Cancel end notification
        val endKey = getIntentKey(taskId, false)
        pendingIntents[endKey]?.let {
            alarmManager.cancel(it)
            it.cancel()
        }
        pendingIntents.remove(endKey)
    }

    private fun scheduleAlarm(taskId: Long, taskName: String, time: Long, isStart: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            return
        }

        val intent = createAlarmIntent(taskId, isStart).apply {
            putExtra("taskName", taskName)
        }

        val requestCode = getRequestCode(taskId, isStart)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Store pending intent for later cancellation
        pendingIntents[getIntentKey(taskId, isStart)] = pendingIntent

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(time, null),
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
        }
    }

    private fun getIntentKey(taskId: Long, isStart: Boolean): String {
        return "${taskId}_${if (isStart) "start" else "end"}"
    }

    private fun createAlarmIntent(taskId: Long, isStart: Boolean): Intent {
        return Intent(context, TaskAlarmReceiver::class.java).apply {
            putExtra("taskId", taskId)
            putExtra("isStartNotification", isStart)
        }
    }

    private fun getRequestCode(taskId: Long, isStart: Boolean): Int {
        return if (isStart) taskId.toInt() * 2 else taskId.toInt() * 2 + 1
    }

    private fun calculateStartTime(task: TaskModel): Long {
        return dateFormat.parse("${task.dateTime} ${task.startTime}")?.time ?: 0L
    }

    private fun calculateEndTime(task: TaskModel): Long {
        return dateFormat.parse("${task.dateTime} ${task.endTime}")?.time ?: 0L
    }
}
