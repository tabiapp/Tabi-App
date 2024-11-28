package com.example.tabi.repository

import com.example.tabi.api.RetrofitInstance

class CityRepository {
    suspend fun getCityData(regions: String) = RetrofitInstance.api.getCityData(regions)
}