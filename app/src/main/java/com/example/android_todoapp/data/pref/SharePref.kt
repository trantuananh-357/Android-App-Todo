package com.apero.common.data.pref

import android.content.Context
import androidx.core.content.edit

class SharePref(context: Context) {
    private val sharedPrefName = "${context.applicationContext.packageName}_pref"

    private val sharePref =
        context.applicationContext.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)

    var language: String
        get() = sharePref.getString(SharePrefConst.PREF_LANGUAGE_CODE, null) ?: "en"
        set(value) = sharePref.edit { putString(SharePrefConst.PREF_LANGUAGE_CODE, value) }
}