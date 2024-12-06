package com.example.android_todoapp.ui.splash

import android.content.Intent
import com.example.android_todoapp.R
import com.example.android_todoapp.base.BaseActivity
import com.example.android_todoapp.databinding.ActivitySpashBinding
import com.example.android_todoapp.extension.hideSystemBar
import com.example.android_todoapp.ui.home.HomeActivity

class SplashActivity : BaseActivity<ActivitySpashBinding>() {
    override val layoutId: Int = R.layout.activity_spash

    override fun setupObserver() {
        /* no-op */
    }

    override fun setupListener() {
        binding.txtStart.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        this.hideSystemBar(hideStatusBar = true)
    }

    override fun setupData() {
        /* no-op */
    }

    override fun setupUI() {
        /* no-op */
    }
}