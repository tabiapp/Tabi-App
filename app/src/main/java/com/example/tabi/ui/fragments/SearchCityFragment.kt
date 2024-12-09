package com.example.tabi.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tabi.R
import com.example.tabi.databinding.FragmentSearchCityBinding
import com.example.tabi.model.CityData
import com.example.tabi.ui.activities.CityDetailActivity
import com.example.tabi.ui.adapters.CityAdapter
import com.example.tabi.api.RetrofitInstance
import com.example.tabi.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchCityFragment : Fragment() {

    private lateinit var binding: FragmentSearchCityBinding
    private lateinit var cityAdapter: CityAdapter
    private val cityList = mutableListOf<CityData>()
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchCityBinding.inflate(inflater, container, false)

        // Setup RecyclerView and Adapter
        setupRecyclerView()

        // Initialize API service
        apiService = RetrofitInstance.apiService

        // Preload some city data from API
        loadCityData()

        // Handle Search Button Click
        binding.searchButton.setOnClickListener {
            val cityName = binding.searchEditText.text.toString()
            if (cityName.isNotEmpty()) {
                searchCities(cityName)
            } else {
                Toast.makeText(requireContext(), "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        cityAdapter = CityAdapter { city ->
            navigateToCityDetail(city)
        }
        binding.recyclerViewCities.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCities.adapter = cityAdapter
    }

    private fun loadCityData() {
        lifecycleScope.launch {
            try {
                // Ambil data kota menggunakan ApiService
                val response: Response<List<CityData>> = apiService.getAllRegions()
                if (response.isSuccessful && response.body() != null) {
                    cityList.clear()
                    cityList.addAll(response.body()!!)
                    cityAdapter.submitList(cityList)
                } else {
                    Toast.makeText(requireContext(), "Failed to load city:", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchCities(cityName: String) {
        val filteredCities = cityList.filter { it.name.contains(cityName, ignoreCase = true) }
        if (filteredCities.isNotEmpty()) {
            cityAdapter.submitList(filteredCities)
        } else {
            Toast.makeText(requireContext(), "No cities found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToCityDetail(city: CityData) {
        val intent = Intent(requireContext(), CityDetailActivity::class.java).apply {
            putExtra("CITY_NAME", city.name)
            putExtra("THUMBNAIL_IMG", city.thumbnailImg)
        }
        startActivity(intent)
    }
}
