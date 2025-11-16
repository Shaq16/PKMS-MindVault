package com.example.pkm_2.api

// File: app/src/main/java/com/yourapp/api/LangflowApiService.kt


import com.example.pkm_2.models.LangflowRequest
import com.example.pkm_2.models.LangflowResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface LangflowApiService {

    @Headers("Content-Type: application/json")
    @POST("api/v1/run/{flow_id}")
    suspend fun runFlow(
        @Path("flow_id") flowId: String,
        @Body request: LangflowRequest
    ): Response<LangflowResponse>
}