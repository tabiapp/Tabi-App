package com.example.tabi.api

data class ApiResponse<T>(
    val status: Boolean,
    val message: String?,
    val data: T?
)
