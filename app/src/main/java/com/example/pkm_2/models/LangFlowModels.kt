package com.example.pkm_2.models

import com.google.gson.annotations.SerializedName

// --- Request Models ---

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

// --- Response Models ---

data class LangflowResponse(
    @SerializedName("outputs")
    val outputs: List<Output>? = null,

    @SerializedName("session_id")
    val sessionId: String? = null
)

data class Output(
    @SerializedName("outputs")
    val outputs: List<OutputData>? = null,

    @SerializedName("messages")
    val messages: List<Message>? = null,

    @SerializedName("component_display_name")
    val componentDisplayName: String? = null,

    @SerializedName("component_id")
    val componentId: String? = null
)

data class OutputData(
    @SerializedName("results")
    val results: Results? = null,

    @SerializedName("message")
    val message: Message? = null,

    @SerializedName("artifacts")
    val artifacts: Map<String, Any>? = null
)

data class Results(
    @SerializedName("message")
    val message: Message? = null,

    // This can sometimes be a map/dictionary, so making it flexible is hard with Gson.
    // Let's stick to the most likely structure first.
    @SerializedName("results")
    val values: Map<String, ValueItem>? = null
)

data class ValueItem(
    @SerializedName("message")
    val message: Message? = null
)

data class Message(
    @SerializedName("text")
    val text: String? = null,

    @SerializedName("sender")
    val sender: String? = null,

    @SerializedName("sender_name")
    val senderName: String? = null,

    @SerializedName("session_id")
    val sessionId: String? = null,

    @SerializedName("timestamp")
    val timestamp: String? = null,

    // For when message is nested inside 'results'
    @SerializedName("results")
    val results: Map<String, ValueItem>? = null
)
