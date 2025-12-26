package com.example.pkm_2.models

data class LangFlowResponse(
    val outputs: List<FlowOutput>?
)

data class FlowOutput(
    val outputs: List<ComponentOutput>?
)

data class ComponentOutput(
    val results: Results?,
    val artifacts: Map<String, Any>?,
    val messages: List<MessageData>?,
    val component_display_name: String?,
    val component_id: String?,
    val text: String?  // Legacy field
)

data class Results(
    val message: MessageData?
)

data class MessageData(
    val text: String?,
    val sender: String?,
    val sender_name: String?,
    val session_id: String?,
    val timestamp: String?,
    val flow_id: String?
)