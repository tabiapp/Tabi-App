package com.example.tabi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tabi.R
import com.example.tabi.databinding.ActivitySearchCityBinding
import com.example.tabi.model.CityData
import com.example.tabi.ui.adapters.CityAdapter

class SearchCityActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchCityBinding
    private lateinit var cityAdapter: CityAdapter
    private val cityList = mutableListOf<CityData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadCityData()

        binding.searchButton.setOnClickListener {
            val cityName = binding.searchEditText.text.toString()
            if (cityName.isNotEmpty()) {
                searchCities(cityName)
            } else {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        cityAdapter = CityAdapter { regions ->
            navigateToCityDetail(regions)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = cityAdapter
    }

    private fun loadCityData() {
        cityList.addAll(
            listOf(
                CityData("Jakarta", R.drawable.jakarta_icon),
                CityData("Yogyakarta", R.drawable.yogyakarta_icon),
                CityData("Bali", R.drawable.bali_icon)
            )
        )
        cityAdapter.submitList(cityList)
    }

    private fun searchCities(cityName: String) {
        val filteredCities = cityList.filter { it.name.contains(cityName, ignoreCase = true) }
        if (filteredCities.isNotEmpty()) {
            cityAdapter.submitList(filteredCities)
        } else {
            Toast.makeText(this, "No cities found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToCityDetail(city: CityData) {
        val intent = Intent(this, CityDetailActivity::class.java).apply {
            putExtra("CITY_NAME", city.name)
            putExtra("THUMBNAIL_IMG", city.thumbnailImg)
        }
        startActivity(intent)
        }
}