package com.example.tabi.api

import com.example.tabi.model.CityData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api")
    suspend fun getCityData(
        @Query("regions") regions: String
    ): Response<CityData>
}