package com.example.android_todoapp.ui.home

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.apero.common.data.pref.SharePref
import com.example.android_todoapp.R
import com.example.android_todoapp.base.BaseActivity
import com.example.android_todoapp.databinding.ActivityHomeBinding
import com.example.android_todoapp.model.DONE
import com.example.android_todoapp.ui.edit.EditActivity
import com.example.android_todoapp.ui.home.adapter.CategoriesTaskAdapter
import com.example.android_todoapp.ui.home.adapter.TaskAdapter
import com.example.android_todoapp.ui.setting.SettingActivity
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
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

    }

    override fun setupListener() {
        binding.imgSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        adapterCategoriesTask.onItemClick = { categoriesTaskModel ->
            viewModel.getTaskByTag(categoriesTaskModel.tag)
        }

        adapterTask.onRemoveClick = { taskModel ->
            viewModel.removeTask(taskModel)
        }

        adapterTask.onStateClick = { taskModel ->
            viewModel.updateTask(taskModel)
        }

        binding.txtCreate.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
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


}