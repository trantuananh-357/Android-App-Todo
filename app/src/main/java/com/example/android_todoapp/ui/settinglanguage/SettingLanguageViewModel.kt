package com.example.android_todoapp.ui.settinglanguage

import androidx.lifecycle.ViewModel
import com.apero.common.data.pref.SharePref
import com.example.android_todoapp.data.repository.impl.LanguageRepoImpl
import com.example.android_todoapp.model.LanguageModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingLanguageViewModel(
    private val languageRepo: LanguageRepoImpl,
    private val pref: SharePref
) : ViewModel() {

    private val _languageStateFlow = MutableStateFlow<LanguageState?>(null)
    val languageStateFlow = _languageStateFlow.asStateFlow()
    var isChangeLanguage : Boolean = false

    init {
        _languageStateFlow.value = LanguageState(
            listLanguage = languageRepo.getAllLanguage(),
            currentLanguage = languageRepo.getLanguageCurrent()
        )
    }

    fun setCurrentLanguage() {
        val currentLanguage = languageStateFlow.value?.currentLanguage!!.languageCode
        if (currentLanguage != pref.language) {
            isChangeLanguage = true
            pref.language = currentLanguage
        }
    }

    fun selectLanguage(languageSelected: LanguageModel) {
        _languageStateFlow.value = _languageStateFlow.value?.copy(
            currentLanguage = languageSelected
        )
    }
}

data class LanguageState(
    val currentLanguage: LanguageModel? = null,
    val listLanguage: List<LanguageModel>? = null
)