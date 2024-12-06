package com.example.android_todoapp.ui.setting

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.android_todoapp.R
import com.example.android_todoapp.base.BaseActivity
import com.example.android_todoapp.databinding.ActivitySettingBinding
import com.example.android_todoapp.ui.home.HomeActivity.Companion.IS_CHANGE_LANGUAGE
import com.example.android_todoapp.ui.settinglanguage.SettingLanguageActivity
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_setting

    private val viewModel by viewModel<SettingViewModel>()

    private val openSettingLanguageLaunch =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                recreate()
                viewModel.getLanguageCurrent()
                viewModel.setChangeLanguage()
            }
        }

    override fun setupUI() {}

    override fun setupObserver() {
        viewModel.languageState.filterNotNull().onEach {
            binding.txtLanguage.text = this.getString(it.languageName)
        }.launchIn(lifecycleScope)
    }

    override fun setupListener() {
        binding.apply {
            imgBack.setOnClickListener {
                setResult(RESULT_OK, Intent().apply {
                    putExtra(IS_CHANGE_LANGUAGE, viewModel.changeLanguage)
                })
                finish()
            }
            vLogout.setOnClickListener {}
            vPrivacy.setOnClickListener {}
            vLanguage.setOnClickListener {
                openSettingLanguageLaunch.launch(
                    Intent(
                        this@SettingActivity,
                        SettingLanguageActivity::class.java
                    )
                )
            }
            vPersonal.setOnClickListener {}
            vHelpCenter.setOnClickListener {}
            vAbout.setOnClickListener {}
        }
    }
}