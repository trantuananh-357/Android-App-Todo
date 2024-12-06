package com.example.android_todoapp.data.repository.impl

import com.apero.common.data.pref.SharePref
import com.example.android_todoapp.R
import com.example.android_todoapp.data.repository.LanguageRepo
import com.example.android_todoapp.model.LanguageModel

class LanguageRepoImpl(
    private val pref: SharePref
) : LanguageRepo {
    override fun getAllLanguage(): List<LanguageModel> {
        return listOf(
            LanguageModel("en", R.string.setting_language_en, R.drawable.img_en),
            LanguageModel("fr", R.string.setting_language_fr, R.drawable.img_fr),
            LanguageModel("vn", R.string.setting_language_vn, R.drawable.img_vn),
            LanguageModel("es", R.string.setting_language_es, R.drawable.img_es),
            LanguageModel("zh", R.string.setting_language_zh, R.drawable.img_zh),
            LanguageModel("pt", R.string.setting_language_pt, R.drawable.img_pt),
            LanguageModel("ru", R.string.setting_language_ru, R.drawable.img_ru),
        )
    }

    override fun getLanguageCurrent(): LanguageModel {
        return getAllLanguage().first { it.languageCode == pref.language }
    }
}