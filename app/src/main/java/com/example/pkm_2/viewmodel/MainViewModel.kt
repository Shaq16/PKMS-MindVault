package com.example.pkm_2.viewmodel

<<<<<<< HEAD
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
=======
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pkm_2.api.LangflowApiService
import com.example.pkm_2.api.RetrofitClient
import com.example.pkm_2.models.LangFlowRequest
import com.example.pkm_2.models.LangFlowResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val sessionId = "user-session-1"
    private val flowId = "5bf8f006-d931-4d55-934a-b4d844889ebb"

    private val api = RetrofitClient.instance.create(LangflowApiService::class.java)

    private val _ingestState = MutableStateFlow<UiState>(UiState.Idle)
    val ingestState = _ingestState.asStateFlow()

    private val _queryState = MutableStateFlow<UiState>(UiState.Idle)
    val queryState = _queryState.asStateFlow()

    // ---------------- INGEST ----------------
    fun ingestData(text: String) {
        viewModelScope.launch {
            _ingestState.value = UiState.Loading
            try {
                // ✅ ONLY input_value (no tweaks)
                val request = LangFlowRequest(
                    input_value = text,
                    input_type = "chat",
                    output_type = "chat"
                )


                Log.d("MainViewModel", "Ingest request: $request")

                val response = api.runFlow(flowId, request)

                Log.d("MainViewModel", "Response code: ${response.code()}")

                if (!response.isSuccessful) {
                    val errorBody = response.errorBody()?.string()
                    Log.e("MainViewModel", "Error body: $errorBody")
                    _ingestState.value =
                        UiState.Error("Error ${response.code()}: ${errorBody ?: "Unknown error"}")
                    return@launch
                }

                _ingestState.value = UiState.Success("Data saved successfully")

            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception during ingest", e)
                _ingestState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    // ---------------- QUERY ----------------
    fun queryData(question: String) {
        viewModelScope.launch {
            _queryState.value = UiState.Loading
            try {
                // ✅ ONLY input_value (no tweaks)
                val request = LangFlowRequest(
                    input_value = question,
                    input_type = "chat",
                    output_type = "chat"
                )


                Log.d("MainViewModel", "Query request: $request")

                val response = api.runFlow(flowId, request)

                Log.d("MainViewModel", "Response code: ${response.code()}")

                if (!response.isSuccessful) {
                    val errorBody = response.errorBody()?.string()
                    Log.e("MainViewModel", "Error body: $errorBody")
                    _queryState.value =
                        UiState.Error("Error ${response.code()}: ${errorBody ?: "Unknown error"}")
                    return@launch
                }

                val reply = extractReply(response.body())
                _queryState.value = UiState.Success(reply ?: "No response received")

            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception during query", e)
                _queryState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private fun extractReply(body: LangFlowResponse?): String? {
        return try {
            body?.outputs
                ?.firstOrNull()
                ?.outputs
                ?.firstOrNull()
                ?.results
                ?.message
                ?.text
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error extracting reply", e)
            null
        }
    }
}
>>>>>>> d01ba51 (Initial commit: Android PKM app with Langflow integration)
