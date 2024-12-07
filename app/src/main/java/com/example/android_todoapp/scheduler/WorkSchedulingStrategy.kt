package com.example.android_todoapp.scheduler

import android.content.Context
import androidx.work.*
import com.example.android_todoapp.model.TaskModel
import com.example.android_todoapp.model.TaskSchedulingStrategy
import com.example.android_todoapp.worker.MissedTaskCheckWorker

class WorkSchedulingStrategy(context: Context) : TaskSchedulingStrategy {
    private val workManager = WorkManager.getInstance(context)

    override fun schedule(task: TaskModel) {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<MissedTaskCheckWorker>()
            .setConstraints(constraints)
            .setInputData(
                workDataOf(
                    "taskId" to task.id,
                    "taskName" to task.taskName
                )
            )
            .addTag("task_${task.id}")
            .build()

        workManager.enqueueUniqueWork(
            "missed_task_${task.id}",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    override fun cancel(taskId: Long) {
        workManager.cancelUniqueWork("missed_task_$taskId")
    }
}
