package com.example.android_todoapp.scheduler

import com.example.android_todoapp.data.repository.ITaskRepo
import com.example.android_todoapp.model.TaskModel
import com.example.android_todoapp.model.TaskScheduler
import com.example.android_todoapp.model.TaskSchedulingStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.text.SimpleDateFormat
import java.util.*

class TaskSchedulerImpl(
    private val alarmStrategy: TaskSchedulingStrategy,
    private val workStrategy: TaskSchedulingStrategy
) : TaskScheduler {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private val taskRepo: ITaskRepo by inject(ITaskRepo::class.java)
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun scheduleTask(task: TaskModel) {
        val currentTime = System.currentTimeMillis()
        val startTime = getTaskTime(task.dateTime, task.startTime)
        val endTime = getTaskTime(task.dateTime, task.endTime)

        when {
            startTime > currentTime -> {
                alarmStrategy.schedule(task)
            }

            endTime < currentTime -> {
                workStrategy.schedule(task)
            }

            else -> {
                scheduleEndNotificationOnly(task)
            }
        }
    }

    private fun scheduleEndNotificationOnly(task: TaskModel) {
        val endTime = getTaskTime(task.dateTime, task.endTime)
        if (endTime > System.currentTimeMillis()) {
            (alarmStrategy as AlarmSchedulingStrategy).scheduleEndAlarm(task)
        }
    }

    private fun getTaskTime(date: String, time: String): Long {
        return try {
            dateFormat.parse("$date $time")?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

    override fun cancelTask(taskId: Long) {
        alarmStrategy.cancel(taskId)
        workStrategy.cancel(taskId)
    }


    override fun rescheduleAllTasks() {
        scope.launch {
            taskRepo.getAllTasks().collect { tasks ->
                tasks.filter { it.status == com.example.android_todoapp.model.TODO }.forEach { taskEntity ->
                    val task = taskEntity.toTaskModel()
                    scheduleTask(task)
                }
            }
        }
    }
}