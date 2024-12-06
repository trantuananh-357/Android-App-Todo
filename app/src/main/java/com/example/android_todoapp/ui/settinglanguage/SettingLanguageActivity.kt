package com.example.android_todoapp.ui.settinglanguage

import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_todoapp.R
import com.example.android_todoapp.base.BaseActivity
import com.example.android_todoapp.databinding.ActivitySettingLanguageBinding
import com.example.android_todoapp.extension.getContextWithResources
import com.example.android_todoapp.ui.settinglanguage.adapter.SettingLanguageAdapter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingLanguageActivity : BaseActivity<ActivitySettingLanguageBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_setting_language

    private val viewModel by viewModel<SettingLanguageViewModel>()

    private val settingLanguageAdapter by lazy {
        SettingLanguageAdapter()
    }

    override fun setupUI() {
        binding.rcvLanguage.adapter = settingLanguageAdapter
    }

    override fun setupListener() {
        binding.imgBack.setOnClickListener {
            viewModel.setCurrentLanguage()
            if (viewModel.isChangeLanguage) {
                setResult(RESULT_OK)
            }
            finish()
        }
        settingLanguageAdapter.setOnClickItem = {
            viewModel.selectLanguage(it)
        }
    }

    override fun setupObserver() {
        viewModel.languageStateFlow
            .map { it?.listLanguage }
            .filterNotNull()
            .onEach {
                settingLanguageAdapter.setData(it)
            }.launchIn(lifecycleScope)

        viewModel.languageStateFlow
            .map { it?.currentLanguage }
            .filterNotNull()
            .onEach {
                settingLanguageAdapter.setCurrentLanguage(it)
                getContextWithResources(this, it.languageCode)?.let { it1 ->
                    binding.txtLanguage.text = ContextCompat.getString(
                        it1, R.string.setting_title_personal_language
                    )
                }
            }.launchIn(lifecycleScope)
    }
}