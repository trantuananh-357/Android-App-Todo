package com.example.android_todoapp.ui.dialog.action

import androidx.annotation.StringRes
import com.example.android_todoapp.R
import java.io.Serializable

internal enum class ActionResult(
    @StringRes val title: Int,
    @StringRes val content: Int,
    @StringRes val action: Int,
    @StringRes val actionNegative: Int = R.string.dialog_result_action_no
): Serializable {
    SaveChange(
        R.string.dialog_save_change_title,
        R.string.dialog_save_change_content,
        R.string.dialog_save_change_action
    ),
    Exit(
        R.string.dialog_exit_title,
        R.string.dialog_exit_content,
        R.string.dialog_exit_action,
    ),
    Remove(
        R.string.dialog_remove_title,
        R.string.dialog_remove_content,
        R.string.dialog_remove_action
    ),
}