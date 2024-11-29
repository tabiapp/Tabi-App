package com.example.tabi.model

data class CityData(
    val id: String,
    val name: String,
    val iconResId: Int,
    val thumbnailImg: String,
    val manners: List<String>,
    val foods: List<String>,
    val places:List<String>
)