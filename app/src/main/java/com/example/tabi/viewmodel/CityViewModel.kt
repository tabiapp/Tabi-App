package com.example.tabi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabi.repository.CityRepository
import com.example.tabi.model.CityData
import com.example.tabi.model.Manner
import com.example.tabi.model.Food
import com.example.tabi.model.Place
import kotlinx.coroutines.launch

class CityViewModel(private val repository: CityRepository) : ViewModel() {

    val cityData: MutableLiveData<List<CityData>?> = MutableLiveData()
    val manners: MutableLiveData<List<Manner>> = MutableLiveData()
    val foods: MutableLiveData<List<Food>> = MutableLiveData()
    val places: MutableLiveData<List<Place>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    // Fungsi untuk fetch data city detail berdasarkan nama kota
    fun fetchCityData(regions: String) {
        viewModelScope.launch {
            try {
                val response = repository.getAllRegions(regions)
                if (response.isSuccessful) {
                    val data = response.body() // List<CityData> yang diterima
                    if (data != null) {
                        cityData.postValue(data)  // Menyimpan data ke LiveData
                    } else {
                        errorMessage.postValue("Data not found")
                    }
                } else {
                    errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Exception: ${e.message}")
            }
        }
    }

    // Fungsi untuk mengambil data manners, foods, dan places
    fun fetchRegionDetails(cityName: String) {
        viewModelScope.launch {
            try {
                // Ambil data manners, foods, dan places secara paralel
                val mannersResponse = repository.getManners(cityName)
                val foodsResponse = repository.getFoods(cityName)
                val placesResponse = repository.getPlaces(cityName)

                // Pastikan semua response berhasil
                if (mannersResponse.isSuccessful) {
                    manners.postValue(mannersResponse.body())
                } else {
                    errorMessage.postValue("Error fetching manners: ${mannersResponse.message()}")
                }

                if (foodsResponse.isSuccessful) {
                    foods.postValue(foodsResponse.body())
                } else {
                    errorMessage.postValue("Error fetching foods: ${foodsResponse.message()}")
                }

                if (placesResponse.isSuccessful) {
                    places.postValue(placesResponse.body())
                } else {
                    errorMessage.postValue("Error fetching places: ${placesResponse.message()}")
                }

            } catch (e: Exception) {
                errorMessage.postValue("Exception: ${e.message}")
            }
        }
    }
}
