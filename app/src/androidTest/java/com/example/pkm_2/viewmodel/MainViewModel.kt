package com.example.pkm_2.viewmodel

// File: app/src/main/java/com/yourapp/viewmodel/MainViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pkm_2.repository.LangflowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = LangflowRepository()

    private val _ingestState = MutableStateFlow<UiState>(UiState.Idle)
    val ingestState: StateFlow<UiState> = _ingestState

    private val _queryState = MutableStateFlow<UiState>(UiState.Idle)
    val queryState: StateFlow<UiState> = _queryState

    /**
     * Ingest data into the database
     */
    fun ingestData(data: String) {
        viewModelScope.launch {
            _ingestState.value = UiState.Loading

            val result = repository.ingestData(data)

            _ingestState.value = if (result.isSuccess) {
                UiState.Success(result.getOrNull() ?: "Success")
            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    /**
     * Query data from the database
     */
    fun queryData(question: String) {
        viewModelScope.launch {
            _queryState.value = UiState.Loading

            val result = repository.queryData(question)

            _queryState.value = if (result.isSuccess) {
                UiState.Success(result.getOrNull() ?: "No answer")
            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}

// UI State sealed class
sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val data: String) : UiState()
    data class Error(val message: String) : UiState()
}