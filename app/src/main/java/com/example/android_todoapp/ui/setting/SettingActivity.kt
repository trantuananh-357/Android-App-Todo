package com.example.android_todoapp.ui.setting

import com.example.android_todoapp.R
import com.example.android_todoapp.base.BaseActivity
import com.example.android_todoapp.databinding.ActivitySettingBinding

class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun setupListener() {
        binding.apply {
            imgBack.setOnClickListener {
                finish()
            }
            vLogout.setOnClickListener {}
            vPrivacy.setOnClickListener {}
            vLanguage.setOnClickListener {}
            vPersonal.setOnClickListener {}
            vHelpCenter.setOnClickListener {}
            vAbout.setOnClickListener {}
        }
    }
}