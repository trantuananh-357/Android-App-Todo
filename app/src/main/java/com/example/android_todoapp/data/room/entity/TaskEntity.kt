package com.example.android_todoapp.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(
    tableName = "task_table",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Int = 0,
    val taskName: String,
    val category: String,
    val dateTime: java.sql.Date,
    val startTime: Time,
    val endTime: Time,
    val description: String = "",
    val userId: Int
)