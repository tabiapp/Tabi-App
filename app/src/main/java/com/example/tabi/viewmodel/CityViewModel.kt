package com.example.tabi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabi.repository.CityRepository
import com.example.tabi.model.CityData
import kotlinx.coroutines.launch

class CityViewModel(private val repository: CityRepository) : ViewModel() {

    val cityData: MutableLiveData<List<CityData>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun fetchCityData(regions: String) {
        viewModelScope.launch {
            try {
                val response = repository.getAllRegions(regions)
                if (response.isSuccessful) {
                    cityData.postValue(response.body()) // Akan berisi data yang diperoleh dari API
                } else {
                    errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Exception: ${e.message}")
            }
        }
    }
}
