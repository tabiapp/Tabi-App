package com.example.tabi.model

data class CityData(
    val id: String,
    val name: String,
    val iconResId: Int,
    val thumbnailImg: String,
    val manners: List<Manner>,
    val foods: List<Food>,
    val places:List<Place>
)