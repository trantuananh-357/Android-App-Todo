package com.example.android_todoapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android_todoapp.data.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun createUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    suspend fun getUser(email: String, password: String): Flow<UserEntity>?

    @Query("UPDATE user_table SET phoneNumber = :phoneNumber WHERE userId = :userId")
    suspend fun updatePhoneNumberToUserEntity(phoneNumber: Int?, userId: Int)

    @Query("UPDATE user_table SET avatarPath = :avatarPath WHERE userId = :userId")
    suspend fun updateAvatarPathToUserEntity(avatarPath: String?, userId: Int)

    @Query("UPDATE user_table SET email = :email WHERE userId = :userId")
    suspend fun updateEmailToUserEntity(email: String?, userId: Int)
}