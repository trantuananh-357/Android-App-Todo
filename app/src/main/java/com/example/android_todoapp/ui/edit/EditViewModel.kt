package com.example.android_todoapp.ui.edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_todoapp.data.repository.ITaskRepo
import com.example.android_todoapp.model.*
import com.example.android_todoapp.ui.edit.EditActivity.Companion.ID_TASK_KEY
import com.example.android_todoapp.ui.edit.adapter.EditUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val taskRepo: ITaskRepo,
    private val taskScheduler: TaskScheduler
) : ViewModel() {

    private val _editUiState = MutableStateFlow(
        EditUiState(
            categoriesList = getCategoriesList(),
            statusList = getStatusList(),
            currentTask = TaskModel()
        )
    )
    val editUiState = _editUiState.asStateFlow()

    private val idTask = savedStateHandle.getStateFlow<Long?>(
        ID_TASK_KEY, null
    )

    fun intiTaskEdit() {
        if (idTask.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                taskRepo.getTaskById(idTask.value ?: 0L).map { taskEntity ->
                    taskEntity.toTaskModel()
                }.collect {
                    onCurrentTaskUpdate(it)
                }
            }
        }
    }

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
                content = TODO
            ),
            EditUIModel(
                content = DONE
            ),
        )
    }

    fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            if (idTask.value != null) {
                Log.d("data", "addTask: ${_editUiState.value.currentTask}")
                taskRepo.updateTask(_editUiState.value.currentTask.toTaskEntity())
            } else {
                taskRepo.createTask(_editUiState.value.currentTask.toTaskEntity())
            }
            taskScheduler.scheduleTask(_editUiState.value.currentTask)
        }
    }

    fun onCurrentTaskUpdate(item: TaskModel) {
        _editUiState.update {
            it.copy(
                currentTask = item
            )
        }
    }

}

data class EditUiState(
    val categoriesList: List<EditUIModel>,
    val statusList: List<EditUIModel>,
    val currentTask: TaskModel
)