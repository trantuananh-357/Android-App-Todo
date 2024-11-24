package com.example.android_todoapp.model

import androidx.annotation.DrawableRes

data class TypeTaskModel(
    val title : String,
    @DrawableRes val icon : Int,
    val tag : String,
    val totalTask : Int
)
