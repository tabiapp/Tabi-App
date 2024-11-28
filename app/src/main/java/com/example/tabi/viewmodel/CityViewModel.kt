package com.example.tabi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabi.repository.CityRepository
import com.example.tabi.model.CityData
import kotlinx.coroutines.launch

class CityViewModel : ViewModel() {
    private val repository = CityRepository()

    val cityData: MutableLiveData<CityData> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun fetchCityData(regions: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCityData(regions)
                if (response.isSuccessful) {
                    cityData.postValue(response.body())
                } else {
                    errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Exception: ${e.message}")
            }
        }
    }
}