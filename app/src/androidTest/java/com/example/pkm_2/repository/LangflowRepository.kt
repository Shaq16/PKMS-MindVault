package com.example.pkm_2.repository

// File: app/src/main/java/com/yourapp/repository/LangflowRepository.kt

import com.example.pkm_2.api.RetrofitClient
import com.example.pkm_2.models.ComponentTweak
import com.example.pkm_2.models.LangflowRequest
import com.example.pkm_2.models.LangflowResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LangflowRepository {

    // ✅ Your flow details
    private val flowId = "711a3691-e267-4db3-8a80-d88a4cc5c6f5"
    private val textInputId = "TextInput-lNGc3"  // For ingesting
    private val chatInputId = "ChatInput-gNh1q"  // For querying

    /**
     * Ingest data into the database
     */
    suspend fun ingestData(data: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val request = LangflowRequest(
                tweaks = mapOf(
                    textInputId to ComponentTweak(inputValue = data)
                )
            )

            val response = RetrofitClient.apiService.runFlow(flowId, request)

            if (response.isSuccessful) {
                Result.success("Data added successfully!")
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Query data from the database
     */
    suspend fun queryData(question: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val request = LangflowRequest(
                tweaks = mapOf(
                    chatInputId to ComponentTweak(inputValue = question)
                )
            )

            val response = RetrofitClient.apiService.runFlow(flowId, request)

            if (response.isSuccessful && response.body() != null) {
                val answer = extractAnswer(response.body()!!)
                Result.success(answer)
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Extract the answer from Langflow response
     */
    private fun extractAnswer(response: LangflowResponse): String {
        // Try to get from results first
        response.outputs
            ?.firstOrNull()
            ?.outputs
            ?.firstOrNull()
            ?.results
            ?.values
            ?.firstOrNull()
            ?.message
            ?.text
            ?.let { return it }

        // Try to get from messages
        response.outputs
            ?.firstOrNull()
            ?.messages
            ?.lastOrNull()
            ?.text
            ?.let { return it }

        return "No answer received"
    }
}