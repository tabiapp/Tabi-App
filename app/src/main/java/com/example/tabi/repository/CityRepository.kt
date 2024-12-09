package com.example.tabi.repository

import com.example.tabi.api.RetrofitInstance
import com.example.tabi.model.CityData
import com.example.tabi.model.Manner
import com.example.tabi.model.Food
import com.example.tabi.model.Place
import retrofit2.Response

class CityRepository {

    suspend fun getAllRegions(regions: String): Response<List<CityData>> {
        return RetrofitInstance.apiService.getAllRegions()
    }

    suspend fun getManners(region: String): Response<List<Manner>> {
        return RetrofitInstance.apiService.getManners(region)
    }

    suspend fun getFoods(region: String): Response<List<Food>> {
        return RetrofitInstance.apiService.getFoods(region)
    }

    suspend fun getPlaces(region: String): Response<List<Place>> {
        return RetrofitInstance.apiService.getPlaces(region)
    }
}
