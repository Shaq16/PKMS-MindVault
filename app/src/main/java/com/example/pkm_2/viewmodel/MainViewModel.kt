package com.example.pkm_2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pkm_2.repository.LangflowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class MainUiState {
    object Idle : MainUiState()
    object Loading : MainUiState()
    data class Success(val message: String) : MainUiState()
    data class Error(val message: String) : MainUiState()
}

class MainViewModel(private val repository: LangflowRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Idle)
    val uiState: StateFlow<MainUiState> = _uiState

    fun ingestData(data: String) {
        viewModelScope.launch {
            _uiState.value = MainUiState.Loading
            val result = repository.ingestData(data)
            _uiState.value = result.fold(
                onSuccess = { MainUiState.Success(it) },
                onFailure = { MainUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun queryData(question: String) {
        viewModelScope.launch {
            _uiState.value = MainUiState.Loading
            val result = repository.queryData(question)
            _uiState.value = result.fold(
                onSuccess = { MainUiState.Success(it) },
                onFailure = { MainUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }
}

class MainViewModelFactory(private val repository: LangflowRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}