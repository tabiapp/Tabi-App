package com.example.tabiku.model

data class TranslationResponse(
    val input_text: String,
    val translated_text: String   // The translated text returned from the API
)
