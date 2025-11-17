package com.example.pkm_2.api

import com.example.pkm_2.models.LangflowRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LangflowApiService {

    @POST("api/v1/run/{flow_id}")
    suspend fun runFlow(
        @Path("flow_id") flowId: String,
        @Body request: LangflowRequest
    ): Response<ResponseBody>  // ✅ Changed to ResponseBody to handle any response
}