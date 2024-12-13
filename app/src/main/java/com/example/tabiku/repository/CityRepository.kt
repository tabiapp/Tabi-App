package com.example.tabiku.repository

import android.util.Log
import com.example.tabiku.model.City
import com.example.tabiku.model.CityResponse
import com.example.tabiku.api.ApiService
import com.example.tabiku.model.CityDetailResponse
import com.example.tabiku.model.FoodResponse
import com.example.tabiku.model.Manner
import com.example.tabiku.model.MannerResponse
import com.example.tabiku.model.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CityRepository {

    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://tabiapp-65059410484.asia-southeast2.run.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getAllCities(callback: (List<City>) -> Unit) {
        apiService.getAllCities().enqueue(object : Callback<CityResponse> {
            override fun onResponse(call: Call<CityResponse>, response: Response<CityResponse>) {
                if (response.isSuccessful) {
                    val cityResponse = response.body()
                    cityResponse?.message?.let { cities -> // "message" berisi list kota
                        Log.d("API Response", "Cities found: ${cities.size} items")
                        callback(cities)
                    } ?: run {
                        Log.e("API Response", "Cities not found")
                    }
                } else {
                    Log.e("API Error", "Response failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CityResponse>, t: Throwable) {
                Log.e("API Error", "Error fetching cities: ${t.message}")
            }
        })
    }

    fun getCityDetail(regionName: String, callback: (CityDetailResponse?) -> Unit) {
        apiService.getCityDetail(regionName).enqueue(object : Callback<CityDetailResponse> {
            override fun onResponse(call: Call<CityDetailResponse>, response: Response<CityDetailResponse>) {
                if (response.isSuccessful) {
                    val cityDetailResponse = response.body()
                    if (cityDetailResponse != null && cityDetailResponse.success) {
                        Log.d("API Response", "City detail found: ${cityDetailResponse.message?.name}")
                        callback(cityDetailResponse)
                    } else {
                        Log.e("API Response", "City detail not found or success false")
                        callback(null)
                    }
                } else {
                    Log.e("API Error", "Response failed: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<CityDetailResponse>, t: Throwable) {
                Log.e("API Error", "Error fetching city detail: ${t.message}")
                callback(null)
            }
        })
    }

    fun getManners(regionName: String, callback: (MannerResponse?) -> Unit) {
        apiService.getManners(regionName).enqueue(object : Callback<MannerResponse> {
            override fun onResponse(call: Call<MannerResponse>, response: Response<MannerResponse>) {
                if (response.isSuccessful) {
                    val mannerResponse = response.body()
                    callback(mannerResponse)
                } else {
                    Log.e("API Error", "Failed to fetch manners: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<MannerResponse>, t: Throwable) {
                Log.e("API Error", "Error fetching manners: ${t.message}")
                callback(null)
            }
        })
    }


    fun getFoods(regionName: String, callback: (FoodResponse?) -> Unit) {
        apiService.getFoods(regionName).enqueue(object : Callback<FoodResponse> {
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                if (response.isSuccessful) {
                    val foodResponse = response.body()
                    callback(foodResponse)
                } else {
                    Log.e("API Error", "Failed to fetch foods: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                Log.e("API Error", "Error fetching foods: ${t.message}")
                callback(null)
            }
        })
    }

    fun getPlaces(regionName: String, callback: (PlaceResponse?) -> Unit) {
        apiService.getPlaces(regionName).enqueue(object : Callback<PlaceResponse> {
            override fun onResponse(call: Call<PlaceResponse>, response: Response<PlaceResponse>) {
                if (response.isSuccessful) {
                    val placeResponse = response.body()
                    callback(placeResponse)
                } else {
                    Log.e("API Error", "Failed to fetch places: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                Log.e("API Error", "Error fetching places: ${t.message}")
                callback(null)
            }
        })
    }


}
