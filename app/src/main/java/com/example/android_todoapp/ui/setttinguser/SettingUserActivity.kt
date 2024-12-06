package com.example.android_todoapp.ui.setttinguser

import androidx.activity.OnBackPressedCallback
import com.example.android_todoapp.R
import com.example.android_todoapp.base.BaseActivity
import com.example.android_todoapp.databinding.ActivitySettingUserBinding

class SettingUserActivity : BaseActivity<ActivitySettingUserBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_setting_user

    override fun setupUI() {
    }
    override fun setupListener() {
        binding.apply {
            onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            })
            imgBackPress.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            vEditAvatar.setOnClickListener {

            }
            edtEmail.setOnClickListener {}
            edtPhone.setOnClickListener {}
            txtLogOut.setOnClickListener {

            }
        }
    }
}