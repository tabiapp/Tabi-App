package com.example.tabiku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabiku.model.City
import com.example.tabiku.model.CityDetailResponse
import com.example.tabiku.model.FoodResponse
import com.example.tabiku.model.MannerResponse
import com.example.tabiku.model.PlaceResponse
import com.example.tabiku.repository.CityRepository

class CityViewModel : ViewModel() {
    private val cityRepository = CityRepository()
    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> get() = _cities
    private val _cityDetail = MutableLiveData<CityDetailResponse?>()
    val cityDetail: LiveData<CityDetailResponse?> get() = _cityDetail

    private val _manners = MutableLiveData<MannerResponse?>()
    val manners: LiveData<MannerResponse?> get() = _manners

    private val _foods = MutableLiveData<FoodResponse?>()
    val foods: LiveData<FoodResponse?> get() = _foods

    private val _places = MutableLiveData<PlaceResponse?>()
    val places: LiveData<PlaceResponse?> get() = _places


    fun fetchAllCities() {
        cityRepository.getAllCities { cities ->
            _cities.postValue(cities)
        }
    }

    fun fetchCityDetail(regionName: String) {
        cityRepository.getCityDetail(regionName) { cityDetailResponse ->
            _cityDetail.postValue(cityDetailResponse)
        }
    }

    fun fetchManners(regionName: String) {
        cityRepository.getManners(regionName) { mannerResponse ->
            _manners.postValue(mannerResponse)
        }
    }

    fun fetchFoods(regionName: String) {
        cityRepository.getFoods(regionName) { foodResponse ->
            _foods.postValue(foodResponse)
        }
    }

    fun fetchPlaces(regionName: String) {
        cityRepository.getPlaces(regionName) { placeResponse ->
            _places.postValue(placeResponse)
        }
    }

}