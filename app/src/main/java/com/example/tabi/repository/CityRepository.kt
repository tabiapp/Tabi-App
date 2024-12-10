package com.example.tabi.repository

import com.example.tabi.api.RetrofitInstance
import com.example.tabi.api.ApiResponse
import com.example.tabi.model.CityData
import com.example.tabi.model.Manner
import com.example.tabi.model.Food
import com.example.tabi.model.Place
import retrofit2.Response

class CityRepository {

    // Mengambil semua region
    suspend fun getAllRegions(): Response<ApiResponse<List<CityData>>> {
        return RetrofitInstance.apiService.getAllRegions()
    }

    // Mengambil manners berdasarkan region
    suspend fun getManners(region: String): Response<ApiResponse<List<Manner>>> {
        return RetrofitInstance.apiService.getManners(region)
    }

    // Mengambil foods berdasarkan region
    suspend fun getFoods(region: String): Response<ApiResponse<List<Food>>> {
        return RetrofitInstance.apiService.getFoods(region)
    }

    // Mengambil places berdasarkan region
    suspend fun getPlaces(region: String): Response<ApiResponse<List<Place>>> {
        return RetrofitInstance.apiService.getPlaces(region)
    }

    // Mengambil detail region
    suspend fun getRegionDetails(region: String): Response<ApiResponse<CityData>> {
        return RetrofitInstance.apiService.getRegionDetails(region)
    }
}
