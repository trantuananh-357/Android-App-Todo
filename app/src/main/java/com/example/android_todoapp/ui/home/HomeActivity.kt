package com.example.android_todoapp.ui.home

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.android_todoapp.R
import com.example.android_todoapp.base.BaseActivity
import com.example.android_todoapp.databinding.ActivityHomeBinding
import com.example.android_todoapp.ui.dialog.action.ActionResult
import com.example.android_todoapp.ui.dialog.action.DialogResult
import com.example.android_todoapp.ui.edit.EditActivity
import com.example.android_todoapp.ui.edit.EditActivity.Companion.ID_TASK_KEY
import com.example.android_todoapp.ui.home.adapter.CategoriesTaskAdapter
import com.example.android_todoapp.ui.home.adapter.TaskAdapter
import com.example.android_todoapp.ui.setting.SettingActivity
import com.example.android_todoapp.worker.helper.PermissionHelper
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val layoutId: Int = R.layout.activity_home

    private val adapterCategoriesTask by lazy {
        CategoriesTaskAdapter(this)
    }

    private val adapterTask by lazy {
        TaskAdapter()
    }

    private val viewModel by viewModel<HomeViewModel>()

    override fun setupUI() {
        binding.rvTypeTask.apply {
            itemAnimator = null
            adapter = adapterCategoriesTask
        }

        binding.rvDataTask.apply {
            itemAnimator = null
            adapter = adapterTask
        }
        PermissionHelper(this).checkAndRequestPermissions()

    }

    private val settingLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data?.getBooleanExtra(
                IS_CHANGE_LANGUAGE,
                false
            ) == true
        ) {
            recreate()
        }
    }

    override fun setupListener() {
        binding.imgSetting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            settingLauncher.launch(intent)
        }

        adapterCategoriesTask.onItemClick = { categoriesTaskModel ->
            viewModel.getTaskByTag(categoriesTaskModel.tag)
        }

        adapterTask.onRemoveClick = { taskModel ->
            actionDialogResult(
                ActionResult.Remove,
                onPositiveClick = {
                    viewModel.removeTask(taskModel)
                },
                isShowAds = false,
            )
        }

        adapterTask.onStateClick = { taskModel ->
            viewModel.updateTask(taskModel)
        }

        adapterTask.onItemClick = {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(ID_TASK_KEY, it.id)
            startActivity(intent)
        }

        binding.txtCreate.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun setupData() {
        viewModel.initDataCategoriesTaskModel()
        viewModel.initDataTaskModel()
    }

    override fun setupObserver() {
        viewModel.homeUiState
            .map { it.categoriesTaskModels }
            .onEach {
                adapterCategoriesTask.setData(it)
            }
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .launchIn(lifecycleScope)

        viewModel.homeUiState
            .map { it.taskModels }
            .distinctUntilChanged()
            .onEach {
                adapterTask.setData(it)
            }
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .launchIn(lifecycleScope)
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

    companion object {
        const val IS_CHANGE_LANGUAGE = "IS_CHANGE_LANGUAGE"
    }

}