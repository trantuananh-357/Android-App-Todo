package com.example.android_todoapp.data.repository.impl

import com.example.android_todoapp.data.repository.IDataRepo

class DataRepoImpl(private val taskRepoImpl: TaskRepoImpl, private val userRepoImpl: UserRepoImpl) :
    IDataRepo {
}