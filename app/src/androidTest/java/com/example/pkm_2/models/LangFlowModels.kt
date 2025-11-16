// File: app/src/main/java/com/yourapp/models/LangflowModels.kt
package com.example.pkm_2.models

import com.google.gson.annotations.SerializedName

// Request model
data class LangflowRequest(
    @SerializedName("input_value")
    val inputValue: String? = null,

    @SerializedName("tweaks")
    val tweaks: Map<String, ComponentTweak>
)

data class ComponentTweak(
    @SerializedName("input_value")
    val inputValue: String
)

// Response model
data class LangflowResponse(
    @SerializedName("outputs")
    val outputs: List<Output>?,

    @SerializedName("session_id")
    val sessionId: String?
)

data class Output(
    @SerializedName("outputs")
    val outputs: List<OutputDetail>?,

    @SerializedName("messages")
    val messages: List<Message>?
)

data class OutputDetail(
    @SerializedName("results")
    val results: Map<String, Result>?
)

data class Result(
    @SerializedName("message")
    val message: Message?
)

data class Message(
    @SerializedName("text")
    val text: String?,

    @SerializedName("sender")
    val sender: String?,

    @SerializedName("type")
    val type: String?
)