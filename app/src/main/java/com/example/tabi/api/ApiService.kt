package com.example.tabi.api

import com.example.tabi.model.CityData
import com.example.tabi.model.Manner
import com.example.tabi.model.Food
import com.example.tabi.model.Place
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/regions")
    suspend fun getAllRegions(): Response<List<CityData>>

    @GET("api/{region}/manners")
    suspend fun getManners(@Path("region") region: String): Response<List<Manner>>

    @GET("api/{region}/foods")
    suspend fun getFoods(@Path("region") region: String): Response<List<Food>>

    @GET("api/{region}/places")
    suspend fun getPlaces(@Path("region") region: String): Response<List<Place>>
}
