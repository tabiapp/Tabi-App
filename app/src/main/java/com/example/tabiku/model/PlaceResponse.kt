package com.example.tabiku.model

data class PlaceResponse(
    val success: Boolean,
    val message: List<Place>
)