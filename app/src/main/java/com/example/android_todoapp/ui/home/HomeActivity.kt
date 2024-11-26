package com.example.android_todoapp.ui.home

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.android_todoapp.R
import com.example.android_todoapp.base.BaseActivity
import com.example.android_todoapp.databinding.ActivityHomeBinding
import com.example.android_todoapp.ui.home.adapter.CategoriesTaskAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val layoutId: Int = R.layout.activity_home

    private val adapterCategoriesTask by lazy {
        CategoriesTaskAdapter(this)
    }
    private val viewModel by viewModel<HomeViewModel>()

    override fun setupUI() {
        binding.rvTypeTask.apply {
            itemAnimator = null
            adapter = adapterCategoriesTask
        }
    }

    override fun setupListener() {

    }

    override fun setupData() {
        viewModel.onCategoriesTaskChanged()
    }

    override fun setupObserver() {
        viewModel.homeUiState
            .map { it.categoriesTaskModels }
            .onEach {
                adapterCategoriesTask.setData(it)
            }
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .launchIn(lifecycleScope)
    }


}