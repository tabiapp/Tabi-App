package com.example.tabi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabi.repository.CityRepository
import com.example.tabi.model.CityData
import kotlinx.coroutines.launch

class CityViewModel : ViewModel() {

    private val repository = CityRepository()  // Inisialisasi repository

    private val _cityData = MutableLiveData<List<CityData>?>()
    val cityData: LiveData<List<CityData>?> get() = _cityData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // Fungsi untuk fetch data city detail berdasarkan nama kota
    fun fetchCityData() {
        viewModelScope.launch {
            try {
                val response = repository.getAllRegions()
                if (response.isSuccessful) {
                    _cityData.postValue(response.body()?.data) // Pastikan Anda menyesuaikan dengan struktur data

                } else {
                    _errorMessage.postValue("Error fetching cities: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error: ${e.message}")
            }
        }
    }
    private val _selectedCity = MutableLiveData<CityData?>()
    val selectedCity: LiveData<CityData?> get() = _selectedCity

    fun fetchRegionDetails(cityName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getAllRegions()
                if (response.isSuccessful) {
                    val city = response.body()?.data?.find { it.name.equals(cityName, ignoreCase = true) }
                    _selectedCity.postValue(city)
                } else {
                    _errorMessage.postValue("Error fetching city details: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error: ${e.message}")
            }
        }
    }
}
