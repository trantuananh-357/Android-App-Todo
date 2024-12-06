package com.example.android_todoapp.data.repository

import com.example.android_todoapp.model.LanguageModel

interface LanguageRepo {
    fun  getAllLanguage() : List<LanguageModel>
    fun getLanguageCurrent() : LanguageModel
}