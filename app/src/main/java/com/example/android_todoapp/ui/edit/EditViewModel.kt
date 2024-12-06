package com.example.android_todoapp.ui.edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_todoapp.data.repository.ITaskRepo
import com.example.android_todoapp.model.DAILY_TASK
import com.example.android_todoapp.model.DONE
import com.example.android_todoapp.model.STUDY
import com.example.android_todoapp.model.TaskModel
import com.example.android_todoapp.model.WORK
import com.example.android_todoapp.ui.edit.EditActivity.Companion.ID_TASK_KEY
import com.example.android_todoapp.ui.edit.adapter.EditUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val taskRepo: ITaskRepo
) : ViewModel() {

    private val _editUiState = MutableStateFlow(
        EditUiState(
            categoriesList = getCategoriesList(),
            statusList = getStatusList()
        )
    )

    private val idTask = savedStateHandle.getStateFlow<String?>(
        ID_TASK_KEY, null
    )
    var currentTask = TaskModel()

    val editUiState = _editUiState.asStateFlow()

    private fun getCategoriesList(): List<EditUIModel> {
        return listOf(
            EditUIModel(
                content = WORK
            ),
            EditUIModel(
                content = DAILY_TASK
            ),
            EditUIModel(
                content = STUDY
            )
        )
    }

    private fun getStatusList(): List<EditUIModel> {
        return listOf(
            EditUIModel(
                content = com.example.android_todoapp.model.TODO
            ),
            EditUIModel(
                content = DONE
            ),
        )
    }

    fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("data", "addTask: ${currentTask}")
            taskRepo.createTask(currentTask.toTaskEntity())
        }
    }

}

data class EditUiState(
    val categoriesList: List<EditUIModel>,
    val statusList: List<EditUIModel>
)