package com.example.android_todoapp.di

import com.example.android_todoapp.ui.home.HomeViewModel
import com.example.android_todoapp.ui.setting.SettingViewModel
import com.example.android_todoapp.ui.settinglanguage.SettingLanguageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.lazyModule

val viewModelModule = lazyModule {
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        SettingViewModel(get())
    }
    viewModel {
        SettingLanguageViewModel(get(), get())
    }
}