package com.example.pkm_2.models

data class LangFlowRequest(
    val input_value: String,
    val input_type: String = "chat",
    val output_type: String = "chat",
    val tweaks: Map<String, Map<String, Any>>? = null
)
