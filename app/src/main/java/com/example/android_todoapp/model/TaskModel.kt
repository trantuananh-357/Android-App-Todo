package com.example.android_todoapp.model

data class TaskModel(
    val id : Long,
    val taskName: String,
    val category: String,
    val dateTime: String,
    val startTime: String,
    val status : String,
    val endTime: String,
    val description: String = "",
)
