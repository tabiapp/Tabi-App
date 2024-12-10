package com.example.tabi.api

import com.example.tabi.api.ApiResponse
import com.example.tabi.model.CityData
import com.example.tabi.model.Manner
import com.example.tabi.model.Food
import com.example.tabi.model.Place
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/regions")
    suspend fun getAllRegions(): Response<ApiResponse<List<CityData>>>

    @GET("api/regions/{region}/manners")
    suspend fun getManners(@Path("region") region: String): Response<ApiResponse<List<Manner>>>

    @GET("api/regions/{region}/foods")
    suspend fun getFoods(@Path("region") region: String): Response<ApiResponse<List<Food>>>

    @GET("api/regions/{region}/places")
    suspend fun getPlaces(@Path("region") region: String): Response<ApiResponse<List<Place>>>

    @GET("api/regions/{region}")
    suspend fun getRegionDetails(@Path("region") region: String): Response<ApiResponse<CityData>>
}
