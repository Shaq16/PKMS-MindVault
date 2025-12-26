package com.example.pkm_2.api

<<<<<<< HEAD
import com.example.pkm_2.models.LangflowRequest
import okhttp3.ResponseBody
=======
import com.example.pkm_2.models.LangFlowRequest
import com.example.pkm_2.models.LangFlowResponse
>>>>>>> d01ba51 (Initial commit: Android PKM app with Langflow integration)
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LangflowApiService {

<<<<<<< HEAD
    @POST("api/v1/run/{flow_id}")
    suspend fun runFlow(
        @Path("flow_id") flowId: String,
        @Body request: LangflowRequest
    ): Response<ResponseBody>  // ✅ Changed to ResponseBody to handle any response
}
=======
    @POST("api/v1/run/{flowId}")
    suspend fun runFlow(
        @Path("flowId") flowId: String,
        @Body request: LangFlowRequest
    ): Response<LangFlowResponse>
}
>>>>>>> d01ba51 (Initial commit: Android PKM app with Langflow integration)
