package com.example.android_todoapp.ui.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.android_todoapp.R
import com.example.android_todoapp.base.BaseActivity
import com.example.android_todoapp.databinding.ActivityEditTaskBinding
import com.example.android_todoapp.ui.dialog.action.ActionResult
import com.example.android_todoapp.ui.dialog.action.DialogResult
import com.example.android_todoapp.ui.edit.adapter.EditItemAdapter
import com.example.android_todoapp.ui.edit.adapter.EditUIModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.Locale

class EditActivity : BaseActivity<ActivityEditTaskBinding>() {
    companion object {
        const val ID_TASK_KEY = "ID_TASK_KEY"
    }

    override val layoutId: Int = R.layout.activity_edit_task

    private val viewModel by viewModel<EditViewModel>()

    private val categoriesAdapter by lazy {
        EditItemAdapter(this)
    }

    private val statusAdapter by lazy {
        EditItemAdapter(this)
    }

    override fun setupUI() {
        viewModel.intiTaskEdit()
        binding.rvCategories.apply {
            itemAnimator = null
            adapter = categoriesAdapter
        }

        binding.rvStatus.apply {
            itemAnimator = null
            adapter = statusAdapter
        }

        onBackPressedDispatcher.addCallback {
            actionDialogResult(
                ActionResult.Exit,
                onNegativeClick = {
                    finish()
                },
                onPositiveClick = {
                    viewModel.addTask()
                    this@EditActivity.finish()
                },
                isShowAds = false,
            )
        }
    }

    override fun setupListener() {
        categoriesAdapter.onItemClick = {
            categoriesAdapter.updateItemSelected(it)
            viewModel.onCurrentTaskUpdate(
                viewModel.editUiState.value.currentTask.copy(
                    category = it.content
                )
            )
        }

        statusAdapter.onItemClick = {
            statusAdapter.updateItemSelected(it)
            viewModel.onCurrentTaskUpdate(
                viewModel.editUiState.value.currentTask.copy(
                    status = it.content
                )
            )
        }

        binding.imgBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.txtSaveChanges.setOnClickListener {
            actionDialogResult(
                ActionResult.SaveChange,
                onNegativeClick = {

                },
                onPositiveClick = {
                    viewModel.addTask()
                    this@EditActivity.finish()
                },
                isShowAds = false,
            )
        }

        val calendar = Calendar.getInstance()
        binding.edtDate.apply {
            setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    binding.edtDate.compoundDrawablesRelative[2]?.let { drawableEnd ->
                        val drawableWidth = drawableEnd.bounds.width()
                        if (event.rawX >= (binding.edtDate.right - drawableWidth - binding.edtDate.paddingEnd)) {
                            showDatePickerDialog(calendar) { selectedDate ->
                                val selectedDateTime = "$selectedDate"
                                binding.edtDate.setText(selectedDateTime)
                            }
                            return@setOnTouchListener true
                        }
                    }
                }
                false
            }

            addTextChangedListener {
                viewModel.onCurrentTaskUpdate(
                    viewModel.editUiState.value.currentTask.copy(
                        dateTime = it.toString()
                    )
                )
            }
        }
        setUpTime(binding.edtStartTime, calendar)
        setUpTime(binding.edtEndTime, calendar)

        binding.edtTaskName.addTextChangedListener {
            viewModel.onCurrentTaskUpdate(
                viewModel.editUiState.value.currentTask.copy(
                    taskName = it.toString()
                )
            )
        }

        binding.edtDescription.addTextChangedListener {
            viewModel.onCurrentTaskUpdate(
                viewModel.editUiState.value.currentTask.copy(
                    description = it.toString()
                )
            )
        }
    }

    override fun setupData() {
        /* no-op */
    }

    override fun setupObserver() {
        viewModel.editUiState
            .map {
                it.categoriesList
            }.onEach {
                categoriesAdapter.setData(it)
            }
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .launchIn(lifecycleScope)

        viewModel.editUiState
            .map {
                it.statusList
            }.onEach {
                statusAdapter.setData(it)
            }
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .launchIn(lifecycleScope)

        viewModel.editUiState
            .map { it.currentTask }
            .distinctUntilChanged()
            .onEach {
                binding.edtTaskName.setText(it.taskName)
                binding.edtDescription.setText(it.description)
                binding.edtDate.setText(it.dateTime)
                binding.edtStartTime.setText(it.startTime)
                binding.edtEndTime.setText(it.endTime)
                statusAdapter.updateItemSelected(it.status)
                categoriesAdapter.updateItemSelected(it.category)
            }
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .launchIn(lifecycleScope)
    }

    private fun showDatePickerDialog(
        calendar: Calendar,
        onDateSelected: (String) -> Unit
    ) {
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val selectedDate = String.format(
                    Locale.ENGLISH,
                    "%02d/%02d/%d",
                    dayOfMonth,
                    month + 1,
                    year
                )
                onDateSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePickerDialog(
        calendar: Calendar,
        onTimeSelected: (String) -> Unit
    ) {
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                val selectedTime = String.format(
                    Locale.ENGLISH,
                    "%02d:%02d",
                    hourOfDay,
                    minute
                )
                onTimeSelected(selectedTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun setUpTime(editView: EditText, calendar: Calendar) {
        editView.apply {
            setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    editView.compoundDrawablesRelative[2]?.let { drawableEnd ->
                        val drawableWidth = drawableEnd.bounds.width()
                        if (event.rawX >= (editView.right - drawableWidth - editView.paddingEnd)) {
                            showTimePickerDialog(calendar) { selectedTime ->
                                val selectedDateTime = "$selectedTime"
                                editView.setText(selectedDateTime)
                            }
                        }
                        return@setOnTouchListener true
                    }
                }
                false
            }

            addTextChangedListener {
                when (editView) {
                    binding.edtStartTime -> {
                        viewModel.onCurrentTaskUpdate(
                            viewModel.editUiState.value.currentTask.copy(
                                startTime = it.toString()
                            )
                        )
                    }

                    binding.edtEndTime -> {
                        viewModel.onCurrentTaskUpdate(
                            viewModel.editUiState.value.currentTask.copy(
                                endTime = it.toString()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun actionDialogResult(
        contentDialog: ActionResult,
        onPositiveClick: () -> Unit = {},
        onNegativeClick: () -> Unit = {},
        isShowAds: Boolean = false,
        isShowContent: Boolean = false
    ) {
        DialogResult.newInstance(
            contentDialog,
            object : DialogResult.Listener {
                override fun onPositiveClick() {
                    onPositiveClick()
                }

                override fun onNegativeClick() {
                    onNegativeClick()
                }

            }, isShowAds = isShowAds, isShowContent = isShowContent
        ).show(supportFragmentManager, null)
    }
}
