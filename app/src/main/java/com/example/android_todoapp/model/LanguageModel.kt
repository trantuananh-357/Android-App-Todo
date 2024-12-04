package com.example.android_todoapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class LanguageModel (
    val languageCode : String,
    @StringRes val languageName : Int,
    @DrawableRes val languageFlag : Int
)