package com.example.android_todoapp.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "task_table",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
        )
    ],
    indices = [androidx.room.Index(value = ["taskName"], unique = true)]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Long = 0,
    val taskName: String,
    val category: String,
    val dateTime: String,
    val startTime: String,
    val endTime: String,
    val status : String,
    val description: String = "",
    val userId: Long
)