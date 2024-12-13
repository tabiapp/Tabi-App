package com.example.tabiku.model

data class CityDetail(
    val name: String,
    val thumbnailImg: String,
    val iconCity: String,
    val manners: List<Manner>,
    val foods: ArrayList<Food>,
    val places: List<Place>
)
