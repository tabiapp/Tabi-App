package com.example.tabiku.model

data class FoodResponse(
    val success: Boolean,
    val message: List<Food>
)