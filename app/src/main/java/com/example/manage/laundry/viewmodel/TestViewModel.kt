package com.example.manage.laundry.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.di.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TestViewModel @Inject constructor(
    private val testRepository: TestRepository
) : ViewModel() {

    var uiState by mutableStateOf(TestUiState())
        private set


    fun test() = viewModelScope.launch {
        uiState = uiState.copy(testResponse = testRepository.test())
    }
}

data class TestUiState(
    val testResponse: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
