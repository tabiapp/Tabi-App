package com.example.tabiku.model

data class TranslationRequest(
    val source_language: String,  // e.g., "en" for English
    val target_language: String,  // e.g., "id" for Indonesian
    val text: String             // The text to be translated
)
