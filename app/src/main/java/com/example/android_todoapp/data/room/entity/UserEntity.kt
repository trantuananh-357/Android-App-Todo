package com.example.android_todoapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val userName: String,
    val email: String,
    val password: String,
    val phoneNumber: Int? = null,
    val avatarPath: String? = null
)