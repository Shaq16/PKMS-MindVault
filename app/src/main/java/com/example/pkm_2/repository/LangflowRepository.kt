package com.example.pkm_2.repository

import android.util.Log
import com.example.pkm_2.api.RetrofitClient
import com.example.pkm_2.models.ComponentTweak
import com.example.pkm_2.models.LangflowRequest
import com.example.pkm_2.models.LangflowResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LangflowRepository {

    companion object {
        private const val TAG = "LangflowRepository"
    }

    private val gson = Gson()

    // ✅ Your flow details
private val flowId = "FLOW_ID"
private val textInputId = "TEXT_INPUT_ID"
private val chatInputId = "CHAT_INPUT_ID"


    /**
     * Ingest data into the database
     */
    suspend fun ingestData(data: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Ingesting data: $data")

            val request = LangflowRequest(
                tweaks = mapOf(
                    textInputId to ComponentTweak(inputValue = data)
                )
            )

            val response = RetrofitClient.apiService.runFlow(flowId, request)

            Log.d(TAG, "Ingest response code: ${response.code()}")

            if (response.isSuccessful && response.body() != null) {
                val rawBody = response.body()!!.string()
                Log.d(TAG, "Raw response body: $rawBody")

                // Try to parse as JSON first
                try {
                    val langflowResponse = gson.fromJson(rawBody, LangflowResponse::class.java)
                    Log.d(TAG, "Parsed as JSON successfully")
                    Result.success("Data added successfully!")
                } catch (e: JsonSyntaxException) {
                    // It's a plain string response
                    Log.d(TAG, "Response is plain string: $rawBody")
                    Result.success(rawBody.trim('"')) // Remove quotes if present
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "Ingest error: ${response.code()} - $errorBody")
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Ingest exception: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Query data from the database
     */
    suspend fun queryData(question: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Querying: $question")

            val request = LangflowRequest(
                tweaks = mapOf(
                    chatInputId to ComponentTweak(inputValue = question)
                )
            )

            val response = RetrofitClient.apiService.runFlow(flowId, request)

            Log.d(TAG, "Query response code: ${response.code()}")

            if (response.isSuccessful && response.body() != null) {
                val rawBody = response.body()!!.string()
                Log.d(TAG, "Raw response body: $rawBody")

                // Try to parse as JSON first
                try {
                    val langflowResponse = gson.fromJson(rawBody, LangflowResponse::class.java)
                    Log.d(TAG, "Parsed as JSON successfully")
                    val answer = extractAnswer(langflowResponse)
                    Result.success(answer)
                } catch (e: JsonSyntaxException) {
                    // It's a plain string response
                    Log.d(TAG, "Response is plain string: $rawBody")
                    Result.success(rawBody.trim('"')) // Remove quotes if present
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "Query error: ${response.code()} - $errorBody")
                Result.failure(Exception("Error: ${response.code()} - ${errorBody ?: response.message()}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Query exception: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Extract the answer from Langflow response
     */
    // In LangflowRepository.kt

    private fun extractAnswer(response: LangflowResponse): String {
        Log.d(TAG, "Extracting answer from response")

        // Log the entire structure for debugging
        response.outputs?.forEachIndexed { index, output ->
            Log.d(TAG, "Output $index: component=${output.componentDisplayName}")
            output.outputs?.forEachIndexed { idx, out ->
                Log.d(TAG, "  Nested output $idx: ${out.message?.text}")
            }
            output.messages?.forEachIndexed { idx, msg ->
                Log.d(TAG, "  Message $idx: ${msg.text}")
            }
        }

        // Strategy 1: messages -> text (most common for chat outputs)
        response.outputs
            ?.firstOrNull()
            ?.messages
            ?.lastOrNull()
            ?.text
            ?.let {
                Log.d(TAG, "Found answer via Strategy 1 (messages.text)")
                return it
            }

        // Strategy 2: outputs -> message -> text
        response.outputs
            ?.firstOrNull()
            ?.outputs
            ?.firstOrNull()
            ?.message
            ?.text
            ?.let {
                Log.d(TAG, "Found answer via Strategy 2 (outputs.message.text)")
                return it
            }

        // Strategy 3: results -> message -> text
        response.outputs
            ?.firstOrNull()
            ?.outputs
            ?.firstOrNull()
            ?.results
            ?.message
            ?.text
            ?.let {
                Log.d(TAG, "Found answer via Strategy 3 (results.message.text)")
                return it
            }

        // Strategy 4: results -> values -> message -> text (Corrected)
        response.outputs
            ?.firstOrNull()
            ?.outputs
            ?.firstOrNull()
            ?.results
            ?.values // This is a Map
            ?.values // This gives you a Collection of the map's values
            ?.firstOrNull()
            ?.message
            ?.text
            ?.let {
                Log.d(TAG, "Found answer via Strategy 4 (results.values.message.text)")
                return@let it // ✅ FIXED: Use a labeled return
            }


        Log.w(TAG, "No answer found in any strategy")
        return "No answer received from Langflow. Check logs for response structure."
    }
}
