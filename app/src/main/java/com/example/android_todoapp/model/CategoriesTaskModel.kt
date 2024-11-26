package com.example.android_todoapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringDef
import androidx.annotation.StringRes

data class CategoriesTaskModel(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @CategoriesTaskTag val tag: String,
    val totalTask: Int
)

const val TOTAL = "TOTAL"
const val WORK = "WORK"
const val DAILY_TASK = "DAILY TASK"
const val STUDY = "STUDY"

@StringDef(
    value = [
        TOTAL,
        WORK,
        DAILY_TASK,
        STUDY
    ]
)
@Retention(AnnotationRetention.SOURCE)
annotation class CategoriesTaskTag

