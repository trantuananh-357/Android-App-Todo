package com.example.android_todoapp.ui.setting

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.android_todoapp.data.repository.impl.LanguageRepoImpl
import com.example.android_todoapp.model.LanguageModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingViewModel(
    private val languageRepo: LanguageRepoImpl
) : ViewModel() {
    private val _languageState = MutableStateFlow<LanguageModel?>(null)
    val languageState = _languageState.asStateFlow()

    init {
        getLanguageCurrent()
    }

    fun getLanguageCurrent() {
        _languageState.value = languageRepo.getLanguageCurrent()
    }
}