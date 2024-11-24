package com.example.android_todoapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.android_todoapp.data.repository.impl.DataRepoImpl

class HomeViewModel(private val dataRepoImpl: DataRepoImpl) : ViewModel() {
}

data class HomeUiState(
    val type
)