package com.example.android_todoapp.data.repository.impl

import com.example.android_todoapp.data.repository.IUserRepo
import com.example.android_todoapp.data.room.dao.UserDao
import com.example.android_todoapp.data.room.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class UserRepoImpl(private val userDao: UserDao) : IUserRepo {
    override suspend fun createUser(userEntity: UserEntity) = withContext(Dispatchers.IO) {
        userDao.createUser(userEntity)
    }

    override suspend fun getUser(email: String, password: String): Flow<UserEntity>? {
        return userDao.getUser(email, password)?.flowOn(Dispatchers.IO)
    }

    override suspend fun updatePhoneNumberToUserEntity(phoneNumber: Int?, userId: Int) =
        withContext(Dispatchers.IO) {
            userDao.updatePhoneNumberToUserEntity(phoneNumber, userId)
        }

    override suspend fun updateAvatarPathToUserEntity(avatarPath: String?, userId: Int) =
        withContext(Dispatchers.IO) {
            userDao.updateAvatarPathToUserEntity(avatarPath, userId)
        }

    override suspend fun updateEmailToUserEntity(email: String?, userId: Int) =
        withContext(Dispatchers.IO) {
            userDao.updateEmailToUserEntity(email, userId)
        }
}