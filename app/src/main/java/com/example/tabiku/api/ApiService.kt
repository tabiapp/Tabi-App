package com.example.tabiku.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.tabiku.model.CityResponse
import com.example.tabiku.model.City
import com.example.tabiku.model.CityDetailResponse
import com.example.tabiku.model.FoodResponse
import com.example.tabiku.model.MannerResponse
import com.example.tabiku.model.PlaceResponse

interface ApiService {
    @GET("regions")
    fun getAllCities(): Call<CityResponse>

    @GET("regions/{regionName}")
    fun getCityDetail(@Path("regionName") regionName: String): Call<CityDetailResponse>

    @GET("regions/{regionName}/manners")
    fun getManners(@Path("regionName") regionName: String): Call<MannerResponse>

    @GET("regions/{regionName}/foods")
    fun getFoods(@Path("regionName") regionName: String): Call<FoodResponse>

    @GET("regions/{regionName}/places")
    fun getPlaces(@Path("regionName") regionName: String): Call<PlaceResponse>
}
