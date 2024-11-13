package com.example.android_todoapp.base

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.apero.common.data.pref.SharePref
import com.example.android_todoapp.extension.displayCutout
import com.example.android_todoapp.extension.getContextWithResources
import com.example.android_todoapp.extension.hideSystemBar
import com.example.android_todoapp.extension.insetPaddingSystemBar
import com.example.android_todoapp.model.TypeInsets
import org.koin.android.ext.android.inject

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: VB
    protected var isLightStatusBar: Boolean = true
    protected var isHideStatusBar: Boolean = false
    protected var isHidNavigationBar: Boolean = true
    protected var typeInsetPadding: TypeInsets = TypeInsets.StatusBar
    protected abstract val layoutId: Int
    protected val pref by inject<SharePref>()
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        displayCutout()
        hideSystemBar(
            hideStatusBar = isHideStatusBar,
            hideNavigationBar = isHidNavigationBar,
            isLightStatusBar = isLightStatusBar
        )
        binding.root.insetPaddingSystemBar(type = typeInsetPadding)
        setupObserver()
        setupListener()
        setupUI()
        setupData()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(
            getContextWithResources(
                newBase,
                pref.language
            )
        )
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemBar(
            hideStatusBar = isHideStatusBar,
            hideNavigationBar = isHidNavigationBar,
            isLightStatusBar = isLightStatusBar
        )
    }

    open fun setupUI() {}
    open fun setupData() {}
    open fun setupListener() {}
    open fun setupObserver() {}

}
