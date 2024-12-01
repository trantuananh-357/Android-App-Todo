package com.example.android_todoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apero.common.data.pref.SharePref
import com.example.android_todoapp.R
import com.example.android_todoapp.data.repository.ITaskRepo
import com.example.android_todoapp.model.CategoriesTaskModel
import com.example.android_todoapp.model.CategoriesTaskTag
import com.example.android_todoapp.model.DAILY_TASK
import com.example.android_todoapp.model.DONE
import com.example.android_todoapp.model.STUDY
import com.example.android_todoapp.model.TOTAL
import com.example.android_todoapp.model.TaskModel
import com.example.android_todoapp.model.WORK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val taskRepo: ITaskRepo, private val pref : SharePref) : ViewModel() {
    private val _homeUiState = MutableStateFlow(
        HomeUiState(
            categoriesTaskModels = emptyList(),
            taskModels = emptyList()
        )
    )
    val homeUiState = _homeUiState.asStateFlow()

    private fun getCategoriesTasksModel(): Flow<List<CategoriesTaskModel>> = flow {
        val result = coroutineScope {
            val work = async {
                taskRepo.getCountTaskInCategoryByTag(WORK).firstOrNull() ?: 0
            }
            val dailyTask = async {
                taskRepo.getCountTaskInCategoryByTag(DAILY_TASK).firstOrNull() ?: 0
            }
            val study = async {
                taskRepo.getCountTaskInCategoryByTag(STUDY).firstOrNull() ?: 0
            }

            val total = listOf(work.await(), dailyTask.await(), study.await())
            listOf(
                CategoriesTaskModel(
                    icon = R.drawable.ic_project,
                    tag = TOTAL,
                    totalTask = total.sum(),
                    title = R.string.home_title_tag_total
                ),
                CategoriesTaskModel(
                    icon = R.drawable.ic_work,
                    tag = WORK,
                    totalTask = total[0],
                    title = R.string.home_title_tag_work
                ),
                CategoriesTaskModel(
                    icon = R.drawable.ic_daily_task,
                    tag = DAILY_TASK,
                    totalTask = total[1],
                    title = R.string.home_title_tag_dailytask
                ),
                CategoriesTaskModel(
                    icon = R.drawable.ic_groceries,
                    tag = STUDY,
                    totalTask = total[2],
                    title = R.string.home_title_tag_study
                ),
            )
        }

        emit(result)
    }

    fun initDataTaskModel() {
        taskRepo.getAllTasksByUserId(1)
            .map { taskEntities ->
                _homeUiState.value.copy(
                    taskModels = taskEntities.map { it.toTaskModel() }
                )
            }
            .onEach { newState ->
                _homeUiState.value = newState
                initDataCategoriesTaskModel()
            }
            .launchIn(viewModelScope)
    }

    fun initDataCategoriesTaskModel() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeUiState.update {
                it.copy(
                    categoriesTaskModels = getCategoriesTasksModel().firstOrNull() ?: emptyList()
                )
            }
        }
    }

    fun getTaskByTag(@CategoriesTaskTag tag: String) {
        viewModelScope.launch {
            when (tag) {
                TOTAL -> {
                    initDataTaskModel()
                }

                else -> {
                    taskRepo.getTaskByTag(tag).map { taskEntities ->
                        taskEntities.map { taskEntity -> taskEntity.toTaskModel() }
                    }.collect {
                        _homeUiState.update { taskModels ->
                            taskModels.copy(
                                taskModels = it
                            )
                        }
                    }
                }
            }
        }
    }

    fun removeTask(taskModel: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepo.deleteTask(taskModel.id.toInt())
        }
    }

    fun updateTask(taskModel: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val newTaskModel = taskModel.copy(status = if (taskModel.status == DONE) com.example.android_todoapp.model.TODO else DONE)
                .toTaskEntity(1)

            taskRepo.updateTask(newTaskModel)
        }
    }
}

data class HomeUiState(
    val categoriesTaskModels: List<CategoriesTaskModel>,
    val taskModels: List<TaskModel>
)

