package com.example.android_todoapp.data.repository

import com.example.android_todoapp.data.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    suspend fun createUser(userEntity: UserEntity)
    suspend fun getUser(email: String, password: String): Flow<UserEntity>?
    suspend fun updatePhoneNumberToUserEntity(phoneNumber: Int?, userId: Int)
    suspend fun updateAvatarPathToUserEntity(avatarPath: String?, userId: Int)
    suspend fun updateEmailToUserEntity(email: String?, userId: Int)
}